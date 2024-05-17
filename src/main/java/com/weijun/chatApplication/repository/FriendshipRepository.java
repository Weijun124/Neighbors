package com.weijun.chatApplication.repository;

import com.weijun.chatApplication.EmbedID.FriendshipKey;
import com.weijun.chatApplication.model.Friendship;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipKey> {
    @Query("SELECT f FROM Friendship f WHERE (f.username = :name1 AND f.friendname = :name2) " +
            "OR (f.username = :name2 AND f.friendname = :name1)")
    Optional<Friendship> findFriendshipBetween(@Param("name1") String name1,
                                               @Param("name2") String name2);

    @Query("SELECT f FROM Friendship f WHERE f.friendname = :username AND f.status = 'pending'")
    List<Friendship> findAllPendingRequestsToUser(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Friendship f WHERE f.friendname = :username OR f.username = :username")
    void denyFriend(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Friendship f SET f.status = :value WHERE (f.friendname = " +
            ":friendName AND f.username = :userName) OR (f.friendname = :userName AND " +
            "f.username = :friendName)")
    int updateFriendshipStatus(@Param("friendName") String friendName,
                               @Param("userName") String userName, @Param("value") String value);


    @Query("SELECT f FROM Friendship f WHERE (f.username = :username " +
            "OR f.friendname = :username) AND f.status = 'accepted'")
    List<Friendship> findAllFriend(@Param("username") String username);
}
