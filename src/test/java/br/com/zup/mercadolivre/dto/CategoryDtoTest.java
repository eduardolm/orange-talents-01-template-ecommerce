package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryDtoTest {
    @Test
    public void shouldCreateCategoryDtoInstance() {
        // Arrange and Act
        CategoryDto actualCategoryDto = new CategoryDto(new Category());

        // Assert
        assertNull(actualCategoryDto.getParent());
        assertNull(actualCategoryDto.getId());
        assertNull(actualCategoryDto.getChildren());
        assertNull(actualCategoryDto.getName());
        assertTrue(actualCategoryDto instanceof CategoryDto);
    }

    @Test
    public void shouldGetPropertiesFromCategoryDto() {
        // Arrange
        Category category = new Category();
        category.setParent(new Category());
        category.setId(123L);
        category.setName("Name");
        category.setChildren(new ArrayList<Category>());
        Category category1 = new Category();
        category1.setParent(category);
        category1.setId(123L);
        category1.setName("Name");
        category1.setChildren(new ArrayList<Category>());
        Category category2 = new Category();
        category2.setParent(category1);
        category2.setId(123L);
        category2.setName("Name");
        category2.setChildren(new ArrayList<Category>());
        Category category3 = new Category();
        category3.setParent(category2);
        category3.setId(123L);
        category3.setName("Name");
        category3.setChildren(new ArrayList<Category>());
        Category category4 = new Category();
        category4.setParent(category3);

        // Act
        CategoryDto actualCategoryDto = new CategoryDto(category4);

        // Assert
        assertSame(category3, actualCategoryDto.getParent());
        assertNull(actualCategoryDto.getId());
        assertNull(actualCategoryDto.getName());
        assertNull(actualCategoryDto.getChildren());
    }
}

