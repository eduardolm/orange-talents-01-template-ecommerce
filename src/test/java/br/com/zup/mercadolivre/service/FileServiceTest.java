package br.com.zup.mercadolivre.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {FileService.class})
@ExtendWith(SpringExtension.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Test
    public void testGetObjectFile() throws FileNotFoundException {
        assertEquals(0,
                this.fileService.getObjectFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()).length);
        assertEquals(0,
                this.fileService.getObjectFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()).length);
    }
}

