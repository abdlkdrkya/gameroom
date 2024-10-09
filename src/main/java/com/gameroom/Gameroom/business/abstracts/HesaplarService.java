package com.gameroom.Gameroom.business.abstracts;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.HesapMasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;

public interface HesaplarService {
	
	DataResult<List<Hesaplar>> tumHesaplariGetir();
	
	DataResult<List<HesapMasaUrunleri>> aydakiTumHesapUrunleriGetir(String ay);

	DataResult<List<Hesaplar>> aydakiTumHesaplariGetir(String ay);
	
	DataResult<List<Hesaplar>> masaHesaplariniGetir(int masaID);
	
	DataResult<List<Hesaplar>> tarihtekiButunHesaplariGetir(String tarih);

	DataResult<List<Hesaplar>> tarihtekiMasaninHesaplariGetir(String tarih, int masaID);
	
	DataResult<List<Hesaplar>> tarihAraligindakiHesaplariGetir(String baslangicTarihi,String bitisTarihi, int masaID);
	
	DataResult<List<HesapMasaUrunleri>> tarihAraligindakiHesapUrunleriniGetir(String baslangicTarihi,String bitisTarihi, int masaID);
	
	DataResult<List<Hesaplar>> tarihAyHesaplariGetir(int ay, int masaID);
	
	DataResult<List<HesapMasaUrunleri>> hesapMasaUrunleriniGetir(int hesapID);
	
	Result hesapEkle(int masaID, String hesapOdemeYontemi);
	
	Result hesapHesaplama(int masaID);
	
	Result hesapSil(int hesapID);
	
	Result hesapDuzenle(int hesapID, int hesapToplami, String hesapOdemeYontemi);
	
}
