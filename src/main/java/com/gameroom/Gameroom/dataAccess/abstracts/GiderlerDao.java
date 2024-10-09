package com.gameroom.Gameroom.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.Giderler;

public interface GiderlerDao extends JpaRepository<Giderler, Integer>{

	Giderler getByGiderID(int giderID);
}
