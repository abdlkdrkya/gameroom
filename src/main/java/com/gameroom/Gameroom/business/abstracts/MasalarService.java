package com.gameroom.Gameroom.business.abstracts;

import java.util.List;
import java.util.Set;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Masalar;
import com.gameroom.Gameroom.entities.concretes.Urunler;

public interface MasalarService {
	
	DataResult<Masalar> getByMasaNumarasi(int masaNumarasi);
	
	DataResult<List<Masalar>> butunMasalariGetir();
	
	Result masaEkleme(int masaNumarasi, String masaAdi);
	
	Result masaBilgileriDegistirme(int masaNumarasi, String masaAdi);
	
	Result masayaUrunEkle(int masaID, int urunID);
	
	Result masaninUrununuSil(int masaID, int urunID);
	
	Result masayiAc(int masaID, String masa4Kol);
	
	Result masayiKapat(int masaID, String masa4Kol);

	DataResult<List<MasaUrunleri>> masaUrunleriniGetir(int masaID);
	
	DataResult<List<MasaUrunleri>> butunMasalarinUrunleriniGetir();
	
	Result masayiAktar(int aktarilanMasaID, int aktarilacakMasaID);
	
	Result masaSaatiDuzenle(int masaID, String saat, String dakika);
}
