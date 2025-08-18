package com.travel.blog.controller.tag;

import com.travel.blog.controller.dto.request.BaseTagRequest;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class CreateTagController extends BaseTagController {

    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody Set<BaseTagRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTags(request).stream().map(o -> tagMapper.toResponse(o)).collect(Collectors.toList()));
    }
}
