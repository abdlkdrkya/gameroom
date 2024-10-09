package com.gameroom.Gameroom.business.abstracts;

import java.util.List;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.KisiUrunleri;
import com.gameroom.Gameroom.entities.concretes.Kisiler;

public interface KisilerService {

	Result kisiEkle(String kisiAdi);
	
	DataResult<List<Kisiler>> butunKisileriGetir();
	
	Result kisiHesabiDuzenle(int kisiID, String kisiAdi, String kisiHesabiToplami) ;
	
	Result kisiSil(int kisiID);
	
	Result kisiUrunEkle(int kisiID, int urunID);
	
	DataResult<List<KisiUrunleri>> kisiUrunleriGetir(int kisiID);
	
}
