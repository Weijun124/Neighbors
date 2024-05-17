package com.weijun.chatApplication.repository;

import com.weijun.chatApplication.model.Blocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BlockRepository extends JpaRepository<Blocks,Integer> {

    @Query("SELECT b FROM Blocks b")
    Set<Blocks> findAllTheBlocks();

    @Query("SELECT b FROM Blocks b WHERE b.bid=:blockid")
    Blocks findSingleBlock(@Param("blockid") Integer blockid);

    @Query("SELECT b.name FROM Blocks b WHERE b.bid=:blockid")
    String findblockName(@Param("blockid") Integer blockid);
}
