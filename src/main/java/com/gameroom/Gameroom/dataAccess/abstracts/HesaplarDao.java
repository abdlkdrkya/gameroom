package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.Hesaplar;

public interface HesaplarDao extends JpaRepository<Hesaplar, Integer>{

	Hesaplar getByHesapID(int hesapID);
	
	List<Hesaplar> getByHesapKesilenMasa_MasaID(int masaID);
}
