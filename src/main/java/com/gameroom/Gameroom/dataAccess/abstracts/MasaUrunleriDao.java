package com.gameroom.Gameroom.dataAccess.abstracts;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;

public interface MasaUrunleriDao extends JpaRepository<MasaUrunleri, Integer>{

	List<MasaUrunleri> getByMasa_MasaID(int masaID);
	
	MasaUrunleri getByMasa_MasaIDAndUrun_UrunID(int masaID, int urunID);
}
