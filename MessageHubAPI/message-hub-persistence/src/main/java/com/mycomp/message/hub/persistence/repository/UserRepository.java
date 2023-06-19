package com.mycomp.message.hub.persistence.repository;

import com.mycomp.message.hub.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
