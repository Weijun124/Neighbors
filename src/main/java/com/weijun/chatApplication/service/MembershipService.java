package com.weijun.chatApplication.service;

import com.weijun.chatApplication.DTO.MembershipDTO;
import com.weijun.chatApplication.EmbedID.MembershipId;
import com.weijun.chatApplication.model.Account;
import com.weijun.chatApplication.model.Blocks;
import com.weijun.chatApplication.model.Membership;
import com.weijun.chatApplication.repository.AccountRepository;
import com.weijun.chatApplication.repository.BlockRepository;
import com.weijun.chatApplication.repository.MembershipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Transactional
    public void insertMemberOption(MembershipDTO membershipDTO) {
        System.out.println(membershipDTO.getRequesterid()+"can not add member");
        System.out.println(membershipDTO.getBlockId()+"can not add member");
        Blocks block = blockRepository.findSingleBlock(membershipDTO.getBlockId());
        Account requester = accountRepository.findSingleName(membershipDTO.getRequesterid());
        if (block == null || requester == null) {
            System.out.println("Block or requester not found!");
        }
        createMembership(membershipDTO,requester,block,"pending",false);
    }

    private void createMembership(MembershipDTO dto, Account requester, Blocks block, String memberStatus, Boolean requestStatus) {
        Membership membership = new Membership();
        MembershipId id = new MembershipId(dto.getRequesterid(), dto.getBlockId());
        membership.setId(id);
        membership.setRequester(requester);
        membership.setBlock(block);
        membership.setMemberstatus(memberStatus);
        membership.setCount(0);
        membership.setRequeststatus(requestStatus);
        System.out.println("2 can not add member");
        membershipRepository.save(membership);
        System.out.println("3 can not add member");
        System.out.println(membership + " is created and will be saved.");
    }

    public List<MembershipDTO> getAllRequestersByBlock(int blockId) {
        List<Membership> memberships = membershipRepository.findAllRequester(blockId);
        List<MembershipDTO> dtos = new ArrayList<>();
        for (Membership membership : memberships) {
            MembershipDTO dto = new MembershipDTO(
                    membership.getRequester().getAccountname(),
                    membership.getBlock().getBid(),
                    membership.getMemberstatus(),
                    membership.getCount(),
                    membership.getRequeststatus()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public boolean updateMembershipCount(String username) {
        int newCount = membershipRepository.findTheCount(username)+1;
        System.out.println(newCount + " find the new count");
        int updatedCount = membershipRepository.updateMemberCount(username, newCount);
        return updatedCount > 0;
    }

    public List<MembershipDTO> getAllMembersByBlock(int blockId) {
        List<Membership> memberships = membershipRepository.findAllMember(blockId);
        List<MembershipDTO> dtos = new ArrayList<>();
        for (Membership membership : memberships) {
            MembershipDTO dto = new MembershipDTO(
                    membership.getRequester().getAccountname(),
                    membership.getBlock().getBid(),
                    membership.getMemberstatus(),
                    membership.getCount(),
                    membership.getRequeststatus()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public Membership userStatus(String username) {
        System.out.println("if the user works"+membershipRepository.userStatus(username));
        return membershipRepository.userStatus(username);
    }
}

