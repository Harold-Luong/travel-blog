package com.travel.blog.service;

import com.travel.blog.controller.dto.request.BaseImageRequest;
import com.travel.blog.entity.Image;
import com.travel.blog.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image createImageLocation(BaseImageRequest imageRequest) {
        Image image = Image.builder().imageUrl(imageRequest.getImageUrl().toLowerCase()).description(imageRequest.getDescription()).build();
        return imageRepository.save(image);
    }
}
