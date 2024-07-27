package com.example.facebook_integration.repository;

import com.example.facebook_integration.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<UserGroup, Integer> {
    Optional<UserGroup> findById(int id);
    List<UserGroup> findAll();
}
