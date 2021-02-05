package br.com.zup.mercadolivre.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FileService.class})
@ExtendWith(SpringExtension.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Test
    public void testGetObjectFile() throws FileNotFoundException {
        // Arrange, Act and Assert
        assertEquals(0,
                this.fileService.getObjectFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()).length);
    }
}
