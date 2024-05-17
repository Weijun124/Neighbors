package com.weijun.chatApplication.controller;

import com.weijun.chatApplication.DTO.AccountDTO;
import com.weijun.chatApplication.DTO.BlockDTO;
import com.weijun.chatApplication.DTO.BlockFollowDTO;
import com.weijun.chatApplication.DTO.NeighborDTO;
import com.weijun.chatApplication.service.AccountService;
import com.weijun.chatApplication.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

//create service variable
@RestController
@CrossOrigin
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    private MembershipService membershipService;


    @PostMapping("/addneighbor")
    public ResponseEntity<String> addNeighbor(@RequestBody NeighborDTO neighborDTO) {
        System.out.println("neighbot"+neighborDTO.getNeighborNames());
            boolean result = accountService.addNeighbor(neighborDTO.getUsername(), neighborDTO.getNeighborNames());
            if (result) {
                return ResponseEntity.ok("Neighbors added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .body("You already added him/her");
            }
    }

    @GetMapping("/neighbors/{username}")
    public ResponseEntity<Set<NeighborDTO>> getNeighbors(@PathVariable String username) {
        Set<NeighborDTO> neighbors = accountService.findAllNeighbor(username);
        if (neighbors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(neighbors);
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<?> updateProfile(@RequestBody AccountDTO accountDTO) {
        System.out.println("Updating account: " + accountDTO.getAccountname());
        boolean isUpdated = accountService.updateAccountProfile(accountDTO);
        if (isUpdated) {
            System.out.println("Update successful");
            return ResponseEntity.ok().body("Profile updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update profile.");
        }
    }

    @GetMapping("/allBlocks")
    public ResponseEntity<Set<BlockDTO>> findAllBlocks() {
        return ResponseEntity.ok().body(accountService.findAllBlocks());
    }

    // display all blocks followed by a user
    @GetMapping("/followed/{accountname}")
    public ResponseEntity<Set<BlockDTO>> displayAllBlocksFollow(@PathVariable String accountname) {
        return ResponseEntity.ok().body(accountService.displayAllBlocksFollow(accountname));
    }

    // add a follow to a block for a user
    @PostMapping("/follow")
    public String addUserFollow(@RequestBody BlockFollowDTO blockFollowDTO) {
        return accountService.addUserFollow(blockFollowDTO);
    }
}

