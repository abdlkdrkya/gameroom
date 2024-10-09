package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.KisiUrunleri;

public interface KisiUrunleriDao extends JpaRepository<KisiUrunleri, Integer>{

	List<KisiUrunleri> getByKisi_KisiID(int kisiID);
	KisiUrunleri getByKisi_KisiIDAndUrun_UrunID(int kisiID, int urunID);
	
}
