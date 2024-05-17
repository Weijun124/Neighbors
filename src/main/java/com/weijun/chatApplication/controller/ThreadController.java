package com.weijun.chatApplication.controller;

import com.weijun.chatApplication.DTO.ThreadDTO;
import com.weijun.chatApplication.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/post")
public class ThreadController {
    @Autowired
    private ThreadService threadService;

    @PostMapping("/add")
    public ResponseEntity<String> createThread(@RequestBody ThreadDTO threadDTO) {
        boolean result = threadService.postThread(threadDTO);
        if (result) {
            return ResponseEntity.ok("Thread Created Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("Failed to Create Thread");
        }
    }
    @GetMapping("/view/{accountname}")
    public ResponseEntity<List<ThreadDTO>> viewThread(@PathVariable String accountname) {
        Set<ThreadDTO> posts = threadService.getAllPost(accountname);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new ArrayList<>(posts));
        }
    }
}
