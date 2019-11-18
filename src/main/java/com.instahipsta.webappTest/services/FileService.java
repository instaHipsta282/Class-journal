package com.instahipsta.webappTest.services;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(@RequestParam("file") MultipartFile file);

    boolean deleteFile(String fileName);
}
