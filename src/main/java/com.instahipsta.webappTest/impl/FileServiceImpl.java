package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String saveFile(@RequestParam("file") MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try { file.transferTo(new File(uploadPath + "/" + resultFilename)); }
            catch (IOException e) { e.printStackTrace(); }

            return resultFilename;
        }
        return null;
    }

    @Override
    public boolean deleteFile(String fileName) {
        String absoluteFileName = uploadPath + "/" + fileName;
        File file = new File(absoluteFileName);
        if (file.exists()) {
            return file.delete();
        }
        else return false;
    }

}
