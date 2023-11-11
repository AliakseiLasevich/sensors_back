package com.sensors.monitor.dao;

import com.sensors.monitor.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = "select t from Token t inner join User u on t.user.id = u.id " +
            "where u.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUserId(Integer id);

    Optional<Token> findByToken(String token);
}
