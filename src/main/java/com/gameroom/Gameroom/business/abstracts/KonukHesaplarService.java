package com.gameroom.Gameroom.business.abstracts;

import java.util.List;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;

public interface KonukHesaplarService {

	DataResult<List<KonukHesaplar>> tumHesaplariGetir();

	DataResult<List<KonukHesaplar>> konukHesaplariniGetir(int masaID);
	
	DataResult<List<KonukHesaplar>> tarihtekiButunHesaplariGetir(String tarih);

	DataResult<List<KonukHesaplar>> tarihtekiKonukHesaplariniGetir(String tarih, int masaID);
	
	DataResult<List<KonukHesaplar>> tarihAraligindakiHesaplariGetir(String baslangicTarihi,String bitisTarihi, int masaID);
		
	DataResult<List<KonukHesapUrunleri>> hesapKonukUrunleriniGetir(int hesapID);
	
	Result hesapEkle(int masaID, String hesapOdemeYontemi);
	
	Result hesapHesaplama(int masaID);
	
	Result hesapSil(int hesapID);
	
	Result hesapDuzenle(int hesapID, int hesapToplami, String hesapOdemeYontemi);
}
