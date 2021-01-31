package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryDetailDtoTest {
    @Test
    public void shouldCreateNewCategoryDetailDtoInstance() {
        // Arrange and Act
        CategoryDetailDto actualCategoryDetailDto = new CategoryDetailDto(new Category());

        // Assert
        assertNull(actualCategoryDetailDto.getParent());
        assertNull(actualCategoryDetailDto.getId());
        assertNull(actualCategoryDetailDto.getChildren());
        assertNull(actualCategoryDetailDto.getName());
    }

    @Test
    public void shouldGetCorrectPropertyFromCategoryDetailDto() {
        // Arrange
        Category category = new Category();
        category.setParent(new Category());
        category.setId(123L);
        category.setName("Name");
        category.setChildren(new ArrayList<>());
        Category category1 = new Category();
        category1.setParent(category);
        category1.setId(123L);
        category1.setName("Name");
        category1.setChildren(new ArrayList<>());
        Category category2 = new Category();
        category2.setParent(category1);
        category2.setId(123L);
        category2.setName("Name");
        category2.setChildren(new ArrayList<>());
        Category category3 = new Category();
        category3.setParent(category2);
        category3.setId(123L);
        category3.setName("Name");
        category3.setChildren(new ArrayList<>());
        Category category4 = new Category();
        category4.setParent(category3);

        // Act
        CategoryDetailDto actualCategoryDetailDto = new CategoryDetailDto(category4);

        // Assert
        assertSame(category3, actualCategoryDetailDto.getParent());
        assertNull(actualCategoryDetailDto.getId());
        assertNull(actualCategoryDetailDto.getName());
        assertNull(actualCategoryDetailDto.getChildren());
    }

    @Test
    public void shouldCorrectlySetChildren() {
        // Arrange
        CategoryDetailDto categoryDetailDto = new CategoryDetailDto(new Category());
        ArrayList<Category> categoryList = new ArrayList<>();

        // Act
        categoryDetailDto.setChildren(categoryList);

        // Assert
        assertSame(categoryList, categoryDetailDto.getChildren());
    }

    @Test
    public void shouldCorrectlySetParent() {
        // Arrange
        CategoryDetailDto categoryDetailDto = new CategoryDetailDto(new Category());
        Category category = new Category();

        // Act
        categoryDetailDto.setParent(category);

        // Assert
        assertSame(category, categoryDetailDto.getParent());
    }
}

