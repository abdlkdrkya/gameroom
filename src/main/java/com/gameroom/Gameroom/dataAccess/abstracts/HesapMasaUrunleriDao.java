package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.HesapMasaUrunleri;

public interface HesapMasaUrunleriDao extends JpaRepository<HesapMasaUrunleri, Integer>{
	
	List<HesapMasaUrunleri> getByHesap_HesapID(int hesapID);
	

}
