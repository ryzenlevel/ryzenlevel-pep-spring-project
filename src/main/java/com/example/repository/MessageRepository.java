package com.example.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    //searches database for all messages with specific id
    @Query("FROM Message WHERE postedBy = :accId")
    List<Message> findAllByPostedBy(@Param("accId") int accountId);
}
