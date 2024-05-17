package com.weijun.chatApplication.service;

import com.weijun.chatApplication.DTO.ThreadDTO;
import com.weijun.chatApplication.model.Account;
import com.weijun.chatApplication.model.PostInformation;
import com.weijun.chatApplication.repository.AccountRepository;
import com.weijun.chatApplication.repository.ThreadRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ThreadService {
    private ThreadRepository threadRepository;
    private AccountRepository accountRepository;

    @Transactional
    public boolean postThread(ThreadDTO threadDTO) {
        try {
            System.out.println(threadDTO.getAccountname());
            PostInformation postInformation = new PostInformation();
            Account account = accountRepository.findSingleName(threadDTO.getAccountname());
            postInformation.setAccount(account);
            postInformation.setBlockid(threadDTO.getBlockId());
            postInformation.setFeedtype(threadDTO.getFeedType());
            postInformation.setReceiver(threadDTO.getReceiver());
            postInformation.setSubject(threadDTO.getSubject());
            postInformation.setBody(threadDTO.getBody());
            postInformation.setTime(LocalDate.now());
            threadRepository.save(postInformation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Set<ThreadDTO> getAllPost(String accountname) {
        int userBlock = accountRepository.findUserBlockid(accountname);
        Set<PostInformation> userThreads = threadRepository.findAllFeedForUser(accountname);
        Set<PostInformation> receiverThreads = threadRepository.findAllFeedFromReceiver(accountname);
        Set<PostInformation> blockThreads = threadRepository.findAllFeedFromBlock(userBlock);

        Set<PostInformation> combinedThreads = new HashSet<>();
        if (!userThreads.isEmpty()) {
            combinedThreads.addAll(userThreads);
        }
        if (!receiverThreads.isEmpty()) {
            combinedThreads.addAll(receiverThreads);
        }
        if (!blockThreads.isEmpty()) {
            combinedThreads.addAll(blockThreads);
        }
        Set<ThreadDTO> threadDTOS = new HashSet<>();
        for (PostInformation singlethread : combinedThreads) {
            System.out.println(singlethread.getThreadid()+"how it works");
            ThreadDTO threadDTO = new ThreadDTO(
                    singlethread.getThreadid(),
                    singlethread.getFeedtype(),
                    singlethread.getReceiver(),
                    singlethread.getTime(),
                    singlethread.getSubject(),
                    singlethread.getBody(),
                    singlethread.getBlockid(),
                    singlethread.getAccount().getAccountname()
            );
            threadDTOS.add(threadDTO);
        }
        return threadDTOS;
    }
}
