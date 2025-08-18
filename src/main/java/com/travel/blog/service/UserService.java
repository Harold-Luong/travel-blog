package com.travel.blog.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.travel.blog.controller.user.UserController.UserUpdateRequest;
import com.travel.blog.entity.User;
import com.travel.blog.repository.UserRepository;
import java.io.IOException;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    public User createUser(User u) {
        return userRepository.save(u);
    }

    public User update(Long id, UserUpdateRequest request, MultipartFile file) {
        User u = getUser(id);
        u.setBio(request.bio());
        u.setGivenName(request.givenName());
        u.setFamilyName(request.familyName());
        if (file != null && !file.isEmpty()) {
            String avatar = uploadImage(file);
            u.setAvatarUrl(avatar);
        }
        return userRepository.save(u);
    }

    /**
     * ảnh sẽ nằm trong folder "travel-blog" trên cloudinary
     *
     * @param file
     * @return link ảnh https
     */
    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "travel-blog"));
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

