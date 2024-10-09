package com.gameroom.Gameroom.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gameroom.Gameroom.entities.concretes.Masalar;

public interface MasalarDao extends JpaRepository<Masalar, Integer>{

	Masalar getByMasaNumarasi(int masaNumarasi);
	
	List<Masalar> getByMasaID(int masaNumarasi);
	
	@Query(value = "SELECT u FROM Masalar u ORDER BY masaID")
	List<Masalar> butunMasalariGetir(Pageable page);
}
