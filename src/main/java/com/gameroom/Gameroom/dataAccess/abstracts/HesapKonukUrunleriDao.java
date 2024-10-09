package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;

public interface HesapKonukUrunleriDao extends JpaRepository<KonukHesapUrunleri, Integer>{
	
	List<KonukHesapUrunleri> getByKonukHesap_KonukHesapID(int konukID);

}
