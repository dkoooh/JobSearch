package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();

    CategoryDto getById(Integer id);

    Boolean isExists(Integer id);
}
