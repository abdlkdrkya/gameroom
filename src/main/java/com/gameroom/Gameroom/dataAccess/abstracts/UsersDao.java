package com.gameroom.Gameroom.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.Users;

public interface UsersDao extends JpaRepository<Users, Integer>{

}
