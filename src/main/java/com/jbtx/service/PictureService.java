package com.jbtx.service;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    boolean addPicture(Long id, String describe, MultipartFile files);
}
