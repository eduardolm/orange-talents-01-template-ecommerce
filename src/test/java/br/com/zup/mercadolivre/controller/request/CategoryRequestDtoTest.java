package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryRequestDtoTest {

    @Autowired
    private Validator validator;

    @Autowired
    private CategoryRepository repository;

    @AfterEach
    public void rollbackDatabase() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateNewInstanceOfCategoryRequestDto() {
        CategoryRequestDto category = new CategoryRequestDto("Test category");

        assertTrue(category instanceof CategoryRequestDto);
        assertEquals("Test category", category.getName());
    }

    @Test
    public void testToModel() {
        Category category = (new CategoryRequestDto("Test category").toModel(repository));

        assertTrue(category instanceof Category);
        assertEquals("Test category", category.getName());
    }

    @Test
    public void shouldReturnErrorWhenNameIsBlank() {
        CategoryRequestDto category = new CategoryRequestDto("");

        assertEquals(1, validator.validate(category).size());
    }

    @Test
    public void shouldReturnErrorWhenParentCategoryIdIsZero() {
        CategoryRequestDto category = new CategoryRequestDto("Test category");

        category.setIdParentCategory(0L);

        assertEquals(1, validator.validate(category).size());
    }

    @Test
    public void shouldReturnErrorWhenParentCategoryIdIsNegative() {
        CategoryRequestDto category = new CategoryRequestDto("Test category");

        category.setIdParentCategory(-10L);

        assertEquals(1, validator.validate(category).size());
    }

    @Test
    public void shouldReturnErrorWhenNameIsNotUnique() {
        Category category = (new CategoryRequestDto("Test category")).toModel(repository);
        repository.save(category);

        CategoryRequestDto category2 = new CategoryRequestDto("Test category");

        assertEquals(1, validator.validate(category2).size());
    }

    @Test
    public void shouldSetName() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();

        categoryRequestDto.setName("Name");

        assertEquals("Name", categoryRequestDto.getName());
    }

    @Test
    public void shouldSetIdParentCategory() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();

        categoryRequestDto.setIdParentCategory(1L);

        assertEquals(1L, categoryRequestDto.getIdParentCategory().longValue());
    }

    @Test
    public void shouldToStringReturnAsExpected() {
        assertEquals("CategoryRequestDto{Categoria='null', idCategoriaMae=null}", (new CategoryRequestDto()).toString());
    }
}

