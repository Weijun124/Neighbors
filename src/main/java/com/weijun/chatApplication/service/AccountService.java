package com.weijun.chatApplication.service;

import com.weijun.chatApplication.DTO.AccountDTO;
import com.weijun.chatApplication.DTO.BlockDTO;
import com.weijun.chatApplication.DTO.BlockFollowDTO;
import com.weijun.chatApplication.DTO.NeighborDTO;
import com.weijun.chatApplication.model.Account;
import com.weijun.chatApplication.model.Blocks;
import com.weijun.chatApplication.model.Friendship;
import com.weijun.chatApplication.repository.AccountRepository;
import com.weijun.chatApplication.repository.BlockRepository;
import com.weijun.chatApplication.repository.FriendshipRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "Can not find this accountname";

    private AccountRepository accountRepository;
    private FriendshipRepository friendshipRepository;
    private BlockRepository blockRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Transactional
    public String signUpUser(Account account) {
        boolean userExists = accountRepository.findByUsername(account.getAccountname()).isPresent();
        if (userExists) {
            throw new IllegalArgumentException("Account name has been taken");
        }
        accountRepository.save(account);
        System.out.println("User successfully registered");
        return "User successfully registered";
    }

    @Transactional
    public Friendship sendRequest(Friendship friend) {
        String approverAccountName = friend.getFriendname();
        String requesterAccountName = friend.getUsername();
        boolean exists = friendshipRepository.findFriendshipBetween(requesterAccountName, approverAccountName).isPresent();
        if (approverAccountName.equals(requesterAccountName)) {
            throw new IllegalArgumentException("Cannot send friend request to oneself");
        }
        if (exists) {
            throw new IllegalArgumentException("Friend request already sent or exists");
        }
        if (accountRepository.findByUsername(approverAccountName).isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }
        String approverUsername = friend.getFriendname();
        return accountRepository.findByUsername(approverUsername)
                .map(user -> {
                    return friendshipRepository.save(friend);
                })
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Friendship> listAllPendingRequestsToUser(String username) {
        return friendshipRepository.findAllPendingRequestsToUser(username);
    }

    @Transactional
    public boolean respondToFriendRequest(String username, String friendname, boolean status) {
        String statusValue = status ? "accepted" : "denied";
        int count = friendshipRepository.updateFriendshipStatus(friendname, username, statusValue);
        System.out.println(count);
        return count > 0;
    }

    public List<Friendship> findAllFriend(String username) {
        return friendshipRepository.findAllFriend(username);
    }

    @Transactional
    public boolean addNeighbor(String accountId, String neighborNames) {
        Account account = accountRepository.findSingleName(accountId);
        Account neighbor = accountRepository.findSingleName(neighborNames);
        Set<Account> neighbors=accountRepository.findAllNeighbors(accountId);
        if (neighbors.contains(neighbor)) {
            return false;
        }
        account.getNeighbors().add(neighbor);
        accountRepository.save(account);
        accountRepository.save(neighbor);
        return true;
    }

    @Transactional
    public Set<NeighborDTO> findAllNeighbor(String username) {
        Set<Account> neighbors = accountRepository.findAllNeighbors(username);
        if (neighbors == null) {
            return Collections.emptySet(); // Handle possible nulls gracefully
        }
        return neighbors.stream()
                .map(this::convertToNeighborDTO)
                .collect(Collectors.toSet());
    }

    private NeighborDTO convertToNeighborDTO(Account account) {
        NeighborDTO dto = new NeighborDTO();
        dto.setUsername(account.getAccountname());
        // Ensure neighbors are loaded and converted to set of names
        Set<String> neighborNames = account.getNeighbors().stream()
                .map(Account::getAccountname)
                .collect(Collectors.toSet());
        dto.setNeighborNameset(neighborNames);
        return dto;
    }

    public boolean updateAccountProfile(AccountDTO accountDTO) {
        System.out.println(accountDTO.getLastname());
        int updatedCount = accountRepository.updateAccountProfile(
                accountDTO.getEmail(),
                accountDTO.getFirstname(),
                accountDTO.getLastname(),
                accountDTO.getIntroduction(),
                accountDTO.getAccountname()
        );
        return updatedCount > 0;
    }

    public Set<BlockDTO> findAllBlocks() {
        Set<Blocks> blocks = blockRepository.findAllTheBlocks();
        System.out.println(blocks.toString()+"what block");
        return blocks.stream()
                .map(this::convertToBlockDTO)
                .collect(Collectors.toSet());
    }
    private BlockDTO convertToBlockDTO(Blocks block) {
        return new BlockDTO(block.getBid(), block.getName());
    }

    public Set<BlockDTO> displayAllBlocksFollow(String accountname){
        Set<Blocks> blocks= accountRepository.findAllBlocksFollowedByUser(accountname);
        Set<BlockDTO> blockDTOS=new HashSet<>();
        for(Blocks b:blocks){
            BlockDTO blockFollowDTO=new BlockDTO(b.getBid(),b.getName()){};
            blockDTOS.add(blockFollowDTO);
        }
        return blockDTOS;
    }

    public String addUserFollow(BlockFollowDTO blockFollowDTO){
        Account account = accountRepository.findSingleName(blockFollowDTO.getAccountname());
        Blocks block = blockRepository.findSingleBlock(blockFollowDTO.getBid());
        if (account == null || block == null) {
            return "User or Block not found!";
        }
        if (!account.getBlocks().contains(block)) {
            System.out.println("works for adding user block follow");
            account.getBlocks().add(block);
            accountRepository.save(account);
            return "Follow added successfully!";
        }
        System.out.println("blockfollow failed");
        return "User already follows this block!";
    }
}
