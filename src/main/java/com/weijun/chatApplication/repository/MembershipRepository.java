package com.weijun.chatApplication.repository;

import com.weijun.chatApplication.EmbedID.MembershipId;
import com.weijun.chatApplication.model.Membership;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MembershipRepository extends JpaRepository<Membership, MembershipId> {
    @Query("SELECT m from Membership m where m.block.bid=:value AND m.memberstatus='pending'")
    List<Membership> findAllRequester(@Param("value") int value);

    @Query("SELECT m.count from Membership m where m.requester.accountname=:username AND m.memberstatus='pending'")
    int findTheCount(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Membership m SET m.count = :value WHERE m.requester.accountname=:username")
    int updateMemberCount(@Param("username") String userName,
                          @Param("value") int count);

    @Query("SELECT m from Membership m where m.block.bid=:value AND m.memberstatus='member'")
    List<Membership> findAllMember(@Param("value") int value);

    @Query("SELECT m from Membership m where m.requester.accountname=:value AND m.memberstatus='member'")
    Membership userStatus(@Param("value") String accountname);
}

