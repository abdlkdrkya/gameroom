package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.Konuk;

public interface KonukDao extends JpaRepository<Konuk, Integer>{
	

	Konuk getByKonukID(int konukID);

}
