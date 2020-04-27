package com.learn.repositories;

import com.learn.entities.Room;
import com.learn.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository <Room, Long> {
    Room findById(long id);
    boolean existsByUser1AndUser2(User a, User b);
    Room findByUser1AndUser2(User a, User b);
}
