package com.travel.blog.service;

import com.travel.blog.common.SlugUtil;
import com.travel.blog.controller.dto.request.BaseTagRequest;
import com.travel.blog.controller.dto.response.BaseTagResponse;
import com.travel.blog.entity.Tag;
import com.travel.blog.mapper.TagMapper;
import com.travel.blog.repository.TagRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    /**
     * Tạo mới nhiều tag trong 1 lần - Validate tên - Nếu tag đã tồn tại (theo name và slug) → dùng lại - Nếu chưa tồn tại → tạo tag mới với slug duy nhất - Slug luôn duy nhất, không tạo -1 nếu slug gốc đã tồn tại
     */
    @Transactional
    public Set<Tag> createTags(Set<BaseTagRequest> requests) {
        Set<Tag> result = new HashSet<>();

        // 1. Load toàn bộ slug & name từ DB
        List<Tag> existingTags = tagRepository.findAllByIsDeletedFalse();
        Set<String> existingSlugs = existingTags.stream()
                .map(Tag::getSlug)
                .collect(Collectors.toSet());
        Map<String, Tag> existingNames = existingTags.stream()
                .collect(Collectors.toMap(
                        t -> t.getName().trim().toLowerCase(),
                        t -> t,
                        (t1, t2) -> t1 // nếu trùng name thì lấy 1 cái
                ));

        for (BaseTagRequest req : requests) {
            String nameKey = req.getName().trim().toLowerCase();

            // Nếu name đã tồn tại -> dùng lại
            if (existingNames.containsKey(nameKey)) {
                result.add(existingNames.get(nameKey));
                continue;
            }

            String baseSlug = SlugUtil.generateUniqueSlug(req.getName(), existingSlugs::contains);// check trong cache

            Tag newTag = Tag.builder()
                    .name(req.getName().trim())
                    .slug(baseSlug)
                    .build();

            Tag saved = tagRepository.save(newTag);
            result.add(saved);

            // Cập nhật cache tại chỗ
            existingSlugs.add(baseSlug);
            existingNames.put(nameKey, saved);
        }
        return result;
    }

    /**
     * Cập nhật thông tin Tag - Nếu tên thay đổi → cập nhật slug mới (đảm bảo không trùng)
     */
    @Transactional
    public BaseTagResponse updateTag(Long id, BaseTagRequest request) {
        Tag tag = tagRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        String nameKey = request.getName().trim().toLowerCase();

        // 1. Nếu tên mới trùng với tag khác thì throw
        boolean nameExists = tagRepository.existsByNameIgnoreCaseAndIdNot(nameKey, id);
        if (nameExists) {
            throw new RuntimeException("Tag name already exists: " + nameKey);
        }

        String baseSlug = SlugUtil.generateUniqueSlug(request.getName(), s -> tagRepository.existsBySlugAndNameNotIgnoreCase(s, nameKey));

        // 3. Update
        tag.setName(nameKey.trim());

        tag.setSlug(baseSlug);

        return tagMapper.toResponse(tagRepository.save(tag));
    }

    /**
     * Xóa mềm tag
     */
    @Transactional
    public void softDeleteTag(Long id) {
        Tag tag = getTagById(id);
        tag.setIsDeleted(true);
        tagRepository.save(tag);
    }

    /**
     * Lấy tất cả tag chưa bị xóa
     */
    public List<Tag> getAllTags() {
        return tagRepository.findAllByIsDeletedFalse();
    }

    /**
     * Lấy 1 tag theo ID
     */
    public Tag getTagById(Long id) {
        return tagRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }
}
