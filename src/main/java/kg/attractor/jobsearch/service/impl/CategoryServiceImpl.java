package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDto).toList();
    }

    @Override
    public CategoryDto getById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found category with ID: " + id));

        return convertToDto(category);
    }

    @Override
    public Boolean isExist(Integer id) {
        return categoryRepository.existsById(id);
    }

    private CategoryDto convertToDto (Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent() != null ? convertToDto(category.getParent()) : null)
                .build();
    }
}
