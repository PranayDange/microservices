package com.lcwd.user.service.microservices.repositories;

import com.lcwd.user.service.microservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
