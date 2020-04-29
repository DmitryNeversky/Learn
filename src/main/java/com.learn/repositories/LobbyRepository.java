package com.learn.repositories;

import com.learn.entities.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    Lobby findById(long id);

}
