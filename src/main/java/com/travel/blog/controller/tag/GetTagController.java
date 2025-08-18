package com.travel.blog.controller.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class GetTagController extends BaseTagController {

    @GetMapping
    public ResponseEntity<?> getAllTag() {
        return ResponseEntity.ok(tagMapper.toResponse(tagService.getAllTags()));
    }
}
