package com.example.controller;

import com.example.entity.Forum;
import com.example.service.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/ForumService")

public class ForumController {

    @Autowired
    private ForumService forumService;


    @PostMapping("/addF")
    @ResponseBody
    public ResponseEntity<Object> addForum(@RequestBody Forum forum) {
        try {
            Forum addedForum = forumService.addForum(forum);
            return ResponseEntity.ok(addedForum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publication contains a bad word.");
        }
    }


    @GetMapping("/forumList")
    public List<Forum> fetchForumList() {
        return forumService.fetchForumList();
    }

    @DeleteMapping("/{id}")
    public String deleteForum(@PathVariable Integer id) {
        forumService.deleteForum(id);
        return "redirect:/forum";
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Forum> updateSupplier(@PathVariable Integer id, @RequestBody Forum forum) {
        Optional<Forum> existingSupplier = forumService.getPostById(id);
        if (existingSupplier.isPresent()) {
            forum.setId(id);
            forumService.updateForum(forum);
            return ResponseEntity.ok(forum);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}