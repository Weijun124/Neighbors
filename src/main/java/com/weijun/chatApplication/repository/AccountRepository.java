package com.weijun.chatApplication.repository;

import com.weijun.chatApplication.model.Account;
import com.weijun.chatApplication.model.Blocks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository //this interface is responsible for the data access
//we only can access data through repository, that's way AccountService need to
//construct the AccountRepository
public interface AccountRepository extends JpaRepository<Account,String> {
    @Query("SELECT a FROM Account a WHERE accountname = :username")
    Optional<Account> findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Account a WHERE accountname = :username")
    Account findSingleName(@Param("username") String username);

    @Query("SELECT a FROM Account a JOIN FETCH a.neighbors WHERE a.accountname = :username")
    Set<Account> findAllNeighbors(@Param("username") String username);

    @Query("SELECT a.block.bid FROM Account a WHERE a.accountname = :username")
    Integer findUserBlockid(@Param("username") String username);


    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.email = :email, a.firstname = :firstname, a.lastname = :lastname, " +
            "a.introduction = :introduction WHERE a.accountname = :username")
    int updateAccountProfile(@Param("email") String email,
                             @Param("firstname") String firstname,
                             @Param("lastname") String lastname,
                             @Param("introduction") String introduction,
                             @Param("username") String username);

    @Query("SELECT b FROM Account a JOIN a.blocks b WHERE a.accountname = :username")
    Set<Blocks> findAllBlocksFollowedByUser(@Param("username") String username);
}