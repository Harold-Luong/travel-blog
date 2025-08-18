package com.travel.blog.controller.tag;

import com.travel.blog.controller.dto.request.BaseTagRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class UpdateTagController extends BaseTagController {

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@PathVariable Long id, @RequestBody BaseTagRequest request) {
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }
}
