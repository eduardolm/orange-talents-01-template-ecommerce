package br.com.zup.mercadolivre.controller.request;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertSame;

public class ProductImageRequestDtoTest {
    @Test
    public void testSetImages() {
        ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(new ArrayList<>());
        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();

        productImageRequestDto.setImages(multipartFileList);

        assertSame(multipartFileList, productImageRequestDto.getImages());
    }
}

