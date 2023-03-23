package com.tempalych.fcrdle.server.repository;

import com.tempalych.fcrdle.server.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, String> {
    User findByLogin(String login);

    @Transactional
    @Modifying
    @Query("update User u set u.token = :token where u.login = :login")
    void setToken(@Param("login") String login, @Param("token") String token);
}
