package com.gameroom.Gameroom.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.Kisiler;

public interface KisilerDao extends JpaRepository<Kisiler, Integer>{

	Kisiler getByKisiID(int kisiID);
}
