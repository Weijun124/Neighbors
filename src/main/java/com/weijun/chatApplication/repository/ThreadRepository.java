package com.weijun.chatApplication.repository;



import com.weijun.chatApplication.model.PostInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Set;

public interface ThreadRepository extends JpaRepository<PostInformation,Integer> {
    @Query("SELECT t FROM PostInformation t JOIN FETCH t.account " +
            "WHERE t.account.accountname = :accountname ORDER BY t.time DESC")
    Set<PostInformation> findAllFeedForUser(@Param("accountname") String accountname);

    @Query("SELECT t from PostInformation t where t.receiver= :accountname order by t.time desc")
    Set<PostInformation> findAllFeedFromReceiver(@Param("accountname") String receiver);

    @Query("SELECT t from PostInformation t where t.account.accountname = :accountname and " +
            "t.feedtype = 'friends' order by t.time desc")
    Set<PostInformation> findAllFeedFromFriends(@Param("accountname") String accountname);

    @Query("SELECT t from PostInformation t where t.account.accountname = :accountname and " +
            "t.feedtype = 'neighbors' order by t.time desc")
    Set<PostInformation> findAllFeedFromNeighbors(@Param("accountname") String accountname);

    @Query("SELECT t from PostInformation t where t.blockid = :blockid and t.feedtype = 'block'" +
            " order by t.time desc")
    Set<PostInformation> findAllFeedFromBlock(@Param("blockid") int blockid);
}
