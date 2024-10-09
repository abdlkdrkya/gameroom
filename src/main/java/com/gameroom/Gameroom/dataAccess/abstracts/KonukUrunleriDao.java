package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.KonukUrunleri;

public interface KonukUrunleriDao extends JpaRepository<KonukUrunleri, Integer>{

	
	List<KonukUrunleri> getByKonuk_KonukID(int konukID);
	
	KonukUrunleri getByKonuk_KonukIDAndUrun_UrunID(int konukID, int urunID);
}
