package com.gameroom.Gameroom.business.abstracts;

import java.util.List;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.Urunler;

public interface UrunlerService {

	DataResult<List<Urunler>> butunUrunleriGetir();
	
	DataResult<Urunler> getByUrunID(int urunID);
	
	Result urunEkle(String urunAdi, double urunFiyati, double urunGelisFiyati, String urunCinsi);
	
	Result urunDuzenle(int urunID, String urunAdi, String urunFiyati);
	
	Result urunStokDuzenle(int urunID, int urunAdeti);
	
	Result urunStokEkle (int urunID, int urunAdeti);
	
	Result urunSil(int urunID);
}
