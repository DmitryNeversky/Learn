package com.learn.repositories;

import com.learn.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    Message findById(long id);

    List<Message> findByText(String text);

}