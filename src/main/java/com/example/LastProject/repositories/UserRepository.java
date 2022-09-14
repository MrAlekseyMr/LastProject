package com.example.LastProject.repositories;

import com.example.LastProject.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findByUsernameContains(String username);
    User findByUsernameAndIdNot(String username, Integer id);
}