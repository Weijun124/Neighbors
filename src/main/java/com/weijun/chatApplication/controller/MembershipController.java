package com.weijun.chatApplication.controller;

import com.weijun.chatApplication.DTO.MembershipDTO;
import com.weijun.chatApplication.model.Membership;
import com.weijun.chatApplication.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertMember(@RequestBody MembershipDTO membershipDTO) {
        try {
            membershipService.insertMemberOption(membershipDTO);
            return ResponseEntity.ok("Membership successfully added");
        } catch (Exception e) {
            System.out.println(membershipDTO.getRequesterid()+"failed to get username");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add membership: " + e.getMessage());
        }
    }
    // Endpoint to get all pending memberships for a specific block

    @GetMapping("/pending/{blockId}")
    public ResponseEntity<List<MembershipDTO>> getPendingMemberships(@PathVariable int blockId) {
        List<MembershipDTO> pendingMemberships = membershipService.getAllRequestersByBlock(blockId);
        return ResponseEntity.ok(pendingMemberships);
    }

    // Endpoint to get all approved memberships for a specific block
    @GetMapping("/members/{blockId}")
    public ResponseEntity<List<MembershipDTO>> getApprovedMemberships(@PathVariable int blockId) {
        if(membershipService.getAllMembersByBlock(blockId).isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            List<MembershipDTO> approvedMemberships = membershipService.getAllMembersByBlock(blockId);
            return ResponseEntity.ok(approvedMemberships);
        }
    }

    // Endpoint to update count for a specific membership
    @PutMapping("/updatecount/{username}")
    public ResponseEntity<String> updateMemberCount(@PathVariable String username) {
        if (membershipService.updateMembershipCount(username)) {
            return ResponseEntity.ok("Count updated successfully");
        }
        return ResponseEntity.badRequest().body("Failed to update count");
    }


    @GetMapping("/userStatus/{username}")
    public ResponseEntity<?> userStatus(@PathVariable String username) {
        Membership membership = membershipService.userStatus(username);
        if (membership == null) {
            return ResponseEntity.notFound().build();
        }
        MembershipDTO dto = new MembershipDTO(
                membership.getRequester().getAccountname(),
                membership.getBlock().getBid(),
                membership.getMemberstatus(),
                membership.getCount(),
                membership.getRequeststatus()
        );
        return ResponseEntity.ok(dto);
    }

}

