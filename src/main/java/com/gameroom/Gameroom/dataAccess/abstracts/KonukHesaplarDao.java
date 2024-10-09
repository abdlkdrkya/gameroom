package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;

public interface KonukHesaplarDao extends JpaRepository<KonukHesaplar, Integer>{

	KonukHesaplar getByKonukHesapID(int konukHesapID);
	
	List<KonukHesaplar> getByHesapKesilenKonuk_KonukID(int konukID);
}
