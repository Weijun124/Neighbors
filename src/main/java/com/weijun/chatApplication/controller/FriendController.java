package com.weijun.chatApplication.controller;

import com.weijun.chatApplication.model.Friendship;
import com.weijun.chatApplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/api/friends")
public class FriendController {
    private final AccountService accountService;

    @Autowired
    public FriendController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/friendrequest")
    public ResponseEntity<?> sendFriendRequest(@RequestBody Friendship friend) {
        try {
            Friendship result = accountService.sendRequest(friend);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/pendingRequests/{username}")
    public ResponseEntity<?> listPendingRequests(@PathVariable String username) {
        try {
            List<Friendship> pendingRequests = accountService.listAllPendingRequestsToUser(username);
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not retrieve pending requests: " + e.getMessage());
        }
    }

    @PostMapping("/respond")
    public ResponseEntity<?> respondToFriendRequest(@RequestParam String username, @RequestParam String friendname, @RequestParam boolean status) {
        try {
            boolean result = accountService.respondToFriendRequest(username, friendname, status);
            if (result) {
                return ResponseEntity.ok("Friend request " + (status ? "accepted" : "denied"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pending friend request found or update failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/allFriend/{username}")
    public ResponseEntity<?> listAllFriend(@PathVariable String username) {
        try {
            List<Friendship> listAllFriend = accountService.findAllFriend(username);
            return ResponseEntity.ok(listAllFriend);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You stay alone" + e.getMessage());
        }
    }
}
