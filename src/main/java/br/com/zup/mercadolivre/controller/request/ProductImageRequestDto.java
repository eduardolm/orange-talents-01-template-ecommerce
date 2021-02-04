package br.com.zup.mercadolivre.controller.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ProductImageRequestDto {

    @Size(min = 1, message = "Obrigatório enviar pelo menos uma imagem do produto.")
    @NotNull(message = "Item orbigatório.")
    private List<MultipartFile> images = new ArrayList<>();

    public ProductImageRequestDto(
            @Size(min = 1, message = "Obrigatório enviar pelo menos uma imagem do produto.")
            @NotNull(message = "Item obrigatório.") List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
