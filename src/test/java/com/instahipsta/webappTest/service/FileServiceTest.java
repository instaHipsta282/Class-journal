package com.instahipsta.webappTest.service;

import com.instahipsta.webappTest.impl.FileServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class FileServiceTest {

    @Autowired
    private FileServiceImpl fileService;

    private MultipartFile multipartFile;

    @Before
    public void createMultipartFile() {
        Path path = Paths.get("test-uploads/rick-y-morty.jpg");
        String name = "rick-y-morty.jpg";
        String originalFileName = "rick-y-morty.jpg";
        String contentType = "image/jpeg";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        }
        catch (IOException e) { e.printStackTrace(); }
        this.multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
    }

    @Test
    public void saveFile() {
        String resultFileName = fileService.saveFile(multipartFile);
        fileService.deleteFile(resultFileName);
        Assert.assertNotNull(resultFileName);
    }

    @Test
    public void deleteFile() {
        String resultFileName = fileService.saveFile(multipartFile);
        Assert.assertTrue(fileService.deleteFile(resultFileName));
    }
}
