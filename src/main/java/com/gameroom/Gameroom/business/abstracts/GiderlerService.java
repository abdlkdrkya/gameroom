package com.gameroom.Gameroom.business.abstracts;

import java.util.List;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.Giderler;

public interface GiderlerService {
	
	Result giderEkle(String giderAdi, double giderFiyati);
	
	Result giderSil(int giderID);
	
	Result giderDuzenle(int giderID, String giderAdi, double giderFiyati);
	
	DataResult<Giderler> giderGetir(int giderID);
	
	DataResult<List<Giderler>> giderlerGetir();
	
	DataResult<List<Giderler>> aydakiGiderleriGetir(String ay);
	
	DataResult<List<Giderler>> tarihAraligindakiGiderleriGetir(String baslangicTarihi, String bitisTarihi);
	
}
