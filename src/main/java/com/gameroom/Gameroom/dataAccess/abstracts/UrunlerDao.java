package com.gameroom.Gameroom.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.entities.concretes.Urunler;

public interface UrunlerDao extends JpaRepository<Urunler, Integer>{
	
	Urunler getByUrunID(int urunID);
	
}
