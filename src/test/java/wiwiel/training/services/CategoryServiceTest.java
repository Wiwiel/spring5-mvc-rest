package wiwiel.training.services;

import wiwiel.training.api.v1.mapper.CategoryMapper;
import wiwiel.training.api.v1.model.CategoryDTO;
import wiwiel.training.domain.Category;
import wiwiel.training.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        assertEquals(3, categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}