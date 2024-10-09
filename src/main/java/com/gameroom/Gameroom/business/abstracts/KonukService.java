package com.gameroom.Gameroom.business.abstracts;

import java.util.List;

import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;
import com.gameroom.Gameroom.entities.concretes.Konuk;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;
import com.gameroom.Gameroom.entities.concretes.KonukUrunleri;

public interface KonukService {
	
	Result konukEkle(String konukIsim); 
	
	Result konukIsimDegistir(int konukID, String konukIsim);
	
	Result konukSil(int konukID);
	
	Result konukUrunEkle(int konukID, int urunID);
	
	Result konukUrunSil(int konukID, int urunID);
	
	DataResult<List<Konuk>> butunAcikKonuklariGetir();
	
	DataResult<List<KonukUrunleri>> konukUrunleriniGetir(int konukID);
	
	DataResult<List<KonukUrunleri>> butunKonuklarinUrunleriniGetir();
	
	DataResult<List<KonukHesaplar>> aydakiTumKonukHesaplariniGetir(String ay);
	
	DataResult<List<KonukHesaplar>>  tarihAraligindakiKonuklariGetir(String baslangicTarihi, String bitisTarihi);
	
	DataResult<List<KonukHesapUrunleri>> konukHesapUrunleriniGetir(int konukID);
}
