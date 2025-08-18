package com.travel.blog.controller.tag;

import com.travel.blog.service.TagService;
import com.travel.blog.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class DeleteTagController {

    @Autowired
    public TagService tripService;

    @DeleteMapping("/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable Long tagId) {
        tripService.softDeleteTag(tagId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Tag success!");
    }
}

