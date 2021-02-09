package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.utils.builder.CategoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testSetId() {
        Category category = new Category();

        category.setId(123L);

        assertEquals(123L, category.getId().longValue());
    }

    @Test
    public void testSetName() {
        Category category = new Category();

        category.setName("Name");

        assertEquals("Name", category.getName());
    }

    @Test
    public void testSetParent() {
        Category category = new Category();
        Category category1 = new Category();

        category.setParent(category1);

        assertSame(category1, category.getParent());
    }

    @Test
    public void testSetChildren() {
        Category category = new Category();

        category.setChildren(new ArrayList<>());

        assertEquals("Categoria{Nome:'null', Subcategoria:[]}", category.toString());
    }

    @Test
    public void testHashCode() {
        assertEquals(29791, (new Category()).hashCode());
        assertEquals(75062036, (new Category("Name")).hashCode());
    }

    @Test
    public void testHashCode2() {
        Category category = new Category();
        category.setId(123L);

        assertEquals(147994, category.hashCode());
    }

    @Test
    public void testHashCode3() {
        Category category = new Category();
        category.setChildren(new ArrayList<>());

        assertEquals(29792, category.hashCode());
    }

    @Test
    public void testHashCode4() {
        Category category = new Category();
        category.setParent(new Category());
        category.setId(123L);
        category.setName(null);
        category.setChildren(new ArrayList<>());

        Category category1 = new Category();
        category1.setParent(category);
        category1.setId(123L);
        category1.setName(null);
        category1.setChildren(new ArrayList<>());

        Category category2 = new Category();
        category2.setParent(category1);
        category2.setId(123L);
        category2.setName(null);
        category2.setChildren(new ArrayList<>());

        Category category3 = new Category();
        category3.setParent(category2);
        category3.setId(123L);
        category3.setName(null);
        category3.setChildren(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category3);

        Category category4 = new Category();
        category4.setChildren(categoryList);

        assertEquals(177817, category4.hashCode());
    }

    @Test
    public void testHashCode5() {
        Category category = new Category();
        category.setParent(new Category());
        category.setId(123L);
        category.setName(null);
        category.setChildren(new ArrayList<>());

        Category category1 = new Category();
        category1.setParent(category);
        category1.setId(123L);
        category1.setName(null);
        category1.setChildren(new ArrayList<>());

        Category category2 = new Category();
        category2.setParent(category1);
        category2.setId(123L);
        category2.setName(null);
        category2.setChildren(new ArrayList<>());

        Category category3 = new Category();
        category3.setParent(category2);
        category3.setId(123L);
        category3.setName(null);
        category3.setChildren(new ArrayList<>());

        Category category4 = new Category();
        category4.setParent(new Category());
        category4.setId(123L);
        category4.setName(null);
        category4.setChildren(new ArrayList<>());

        Category category5 = new Category();
        category5.setParent(category4);
        category5.setId(123L);
        category5.setName(null);
        category5.setChildren(new ArrayList<>());

        Category category6 = new Category();
        category6.setParent(category5);
        category6.setId(123L);
        category6.setName(null);
        category6.setChildren(new ArrayList<>());

        Category category7 = new Category();
        category7.setParent(category6);
        category7.setId(123L);
        category7.setName(null);
        category7.setChildren(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category7);
        categoryList.add(category3);

        Category category8 = new Category();
        category8.setChildren(categoryList);

        assertEquals(4766592, category8.hashCode());
    }

    @Test
    public void shouldCreateNewCategoryInstance() {
        Category category = new CategoryBuilder()
                .withName("Teste")
                .build();

        assertEquals("Teste", category.getName());
    }

    @Test
    public void testEquals() {
        Category category = new Category();

        Category category1 = new Category();
        category1.setParent(new Category());
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
        category4.setId(123L);
        category4.setName("Name");
        category4.setChildren(new ArrayList<>());

        Category category5 = new Category();
        category5.setParent(category4);
        category5.setId(123L);
        category5.setName("Name");
        category5.setChildren(new ArrayList<>());

        assertNotEquals(category5, category);
    }

    @Test
    public void testEquals3() {
        Category category = new Category();

        assertNotEquals(new Category("Name"), category);
    }

    @Test
    public void testEquals4() {
        Category category = new Category("Name");

        assertEquals(new Category("Name"), category);
    }

    @Test
    public void testEquals5() {
        Category category = new Category("Name");
        category.setChildren(new ArrayList<Category>());

        assertNotEquals(new Category("Name"), category);
    }

    @Test
    public void testToString() {
        assertEquals("Categoria{Nome:'null', Subcategoria:null}", (new Category()).toString());
    }

    @Test
    public void testToString2() {
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

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category3);

        Category category4 = new Category();
        category4.setChildren(categoryList);

        assertEquals("Categoria{Nome:'null', Subcategoria:[Categoria{Nome:'Name', Subcategoria:[]}]}",
                category4.toString());
    }

    @Test
    public void testToString3() {
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
        category4.setParent(new Category());
        category4.setId(123L);
        category4.setName("Name");
        category4.setChildren(new ArrayList<>());

        Category category5 = new Category();
        category5.setParent(category4);
        category5.setId(123L);
        category5.setName("Name");
        category5.setChildren(new ArrayList<>());

        Category category6 = new Category();
        category6.setParent(category5);
        category6.setId(123L);
        category6.setName("Name");
        category6.setChildren(new ArrayList<>());

        Category category7 = new Category();
        category7.setParent(category6);
        category7.setId(123L);
        category7.setName("Name");
        category7.setChildren(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category7);
        categoryList.add(category3);

        Category category8 = new Category();
        category8.setChildren(categoryList);

        assertEquals("Categoria{Nome:'null', Subcategoria:[Categoria{Nome:'Name', Subcategoria:[]}, Categoria{Nome:'Name',"
                + " Subcategoria:[]}]}", category8.toString());
    }

    @Test
    public void shouldTwoCategoryInstancesNotBeEqualExceptIfEmpty() {
        Category category1 = new CategoryBuilder()
                .withName("Teste")
                .build();

        Category category2 = new CategoryBuilder()
                .withName("Testes")
                .build();

        Category category3 = new Category();
        Category category4 = new Category();

        assertNotEquals(category1.hashCode(), category2.hashCode());
        assertEquals(category3, category4);
        assertNotEquals(category3.hashCode(), category1.hashCode());
    }

    @Test
    public void shouldTwoIdenticalCategoriesBeEqual() {
        Category category1 = new CategoryBuilder()
                .withName("Teste")
                .build();

        Category category2 = new CategoryBuilder()
                .withName("Teste")
                .build();

        assertEquals(category2.hashCode(), category1.hashCode());
        assertEquals(category1, category2);
    }

    @Test
    public void shouldCategoryGettersWorkCorrectly() {
        Category category1 = new CategoryBuilder()
                .withName("Teste")
                .build();

        category1.setId(1L);
        category1.setParent(new Category("Parent Test"));

        assertEquals("Teste", category1.getName());
        assertEquals(1L, category1.getId());
        assertEquals("Parent Test", category1.getParent().getName());
    }
}
