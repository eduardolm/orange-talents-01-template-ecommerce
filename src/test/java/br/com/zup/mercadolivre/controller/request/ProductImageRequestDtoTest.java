package br.com.zup.mercadolivre.controller.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

public class ProductImageRequestDtoTest {
    @Test
    public void testSetImages() {
        ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(new ArrayList<>());
        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();

        productImageRequestDto.setImages(multipartFileList);

        assertSame(multipartFileList, productImageRequestDto.getImages());
    }
}

