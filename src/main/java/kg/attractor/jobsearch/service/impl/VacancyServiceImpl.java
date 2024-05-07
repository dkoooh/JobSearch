package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.exception.ForbiddenException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final UserService userService;
    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;

    @Override
    public Boolean exists(Integer vacancyId) {
        return vacancyRepository.existsById(vacancyId);
    }

    @Override
    public List<VacancyDto> getAll() {
        List<Vacancy> list = vacancyRepository.findAll();

        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public VacancyDto getById(int vacancyId) {
        return convertListToDto(
                vacancyRepository.findById(vacancyId).orElseThrow(
                        () -> new NotFoundException("Vacancy not found. The requested vacancy does not exist.")
                )
        );
    }

    @Override
    public List<VacancyDto> getAllByApplicant(int applicantId) {
        List<Vacancy> list = vacancyRepository.findAllByApplicantId(applicantId);
        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public Page<VacancyDto> getAllByEmployer(Authentication auth, int page) {
        List<Vacancy> list = vacancyRepository.findAllByAuthorEmail(auth.getName());
        return toPage(list.stream()
                .map(this::convertListToDto)
                .toList(), PageRequest.of(page, 6));
    }

    @Override
    public List<VacancyDto> getAllActiveByCategory(Integer categoryId) {
        if (!categoryService.isExists(categoryId)) {
            throw new NotFoundException("Invalid category");
        }

        List<Vacancy> list = vacancyRepository.findAllByCategoryIdAndIsActiveTrue(
                categoryId, Sort.by(Sort.Order.desc("createdDate"))
        );
        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public Page<VacancyDto> getAllActive(Integer categoryId, String sortedBy, Integer page) {
        if (categoryId != null && !categoryService.isExists(categoryId)) {
            throw new NotFoundException("Invalid category");
        } // TODO проверки могут дублироваться


        List<Vacancy> list;
        if (categoryId != null) {
            if (sortedBy != null) {
                list = vacancyRepository.findAllByCategoryIdAndIsActiveTrue(
                        categoryId, Sort.by(Sort.Order.desc(sortedBy))
                );
            } else {
                list = vacancyRepository.findAllByCategoryIdAndIsActiveTrue(
                        categoryId, Sort.by(Sort.Order.desc("createdDate"))
                );
            }
        } else {
            if (sortedBy != null) {
                list = vacancyRepository.findAllByIsActiveTrue(Sort.by(Sort.Order.desc(sortedBy)));
            } else {
                list = vacancyRepository.findAllByIsActiveTrue(Sort.by(Sort.Order.desc("createdDate")));
            }
        }

        return toPage(list.stream().map(this::convertListToDto).toList(), PageRequest.of(page, 5));
    }

    @Override
    public void create(VacancyCreateDto dto, Authentication auth){
        Vacancy vacancy = Vacancy.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category")))
                .salary(dto.getSalary())
                .expFrom(dto.getExpFrom())
                .expTo(dto.getExpTo())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : false)
                .createdDate(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .author(userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user email")))
                .build();

        vacancyRepository.save(vacancy);
    }

    @Override
    public void update(VacancyUpdateDto dto, Authentication auth)  {
        if (!Objects.equals(
                vacancyRepository.findById(dto.getId())
                        .orElseThrow(() -> new NotFoundException("Vacancy not found. The requested vacancy does not exist."))
                        .getAuthor().getId(),
                userService.getByEmail(auth.getName()).getId()
            )
        ) {
            throw new ForbiddenException("You do not have permission to edit this vacancy");
        }

        Vacancy vacancy = Vacancy.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .category(categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category")))
                .salary(dto.getSalary())
                .expFrom(dto.getExpFrom())
                .expTo(dto.getExpTo()) // TODO валидация
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : false)
                .author(userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user email")))
                .createdDate(vacancyRepository.getReferenceById(dto.getId()).getCreatedDate())
                .updateTime(LocalDateTime.now())
                .build();

        vacancyRepository.save(vacancy);
    }

    @Override
    public void delete(int vacancyId, String email)  {
        if (!Objects.equals(
                vacancyRepository.findById(vacancyId)
                        .orElseThrow(() -> new NotFoundException("Vacancy not found. The requested vacancy does not exist."))
                        .getAuthor().getId(),
                userService.getByEmail(email).getId()
        )
        ) {
            throw new ForbiddenException("You do not have permission to delete this vacancy");
        }
        vacancyRepository.deleteById(vacancyId);
    }

    private Page<VacancyDto> toPage(List<VacancyDto> vacancies, Pageable pageable){
        if (pageable.getOffset() >= vacancies.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize() > vacancies.size() ?
                vacancies.size() : pageable.getOffset() + pageable.getPageSize()));
        List<VacancyDto> subList = vacancies.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, vacancies.size());
    }

    private VacancyDto convertListToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .category(categoryService.getById(
                        vacancy.getCategory().getId()).getName()) // TODO возможно нужно передать весь CategoryDto
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .author(userService.getById(vacancy.getAuthor().getId()))
                .createdDate(vacancy.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updateTime(vacancy.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
