package com.gameroom.Gameroom.business.concretes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.KonukHesaplarService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorDataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.core.utilities.results.SuccessDataResult;
import com.gameroom.Gameroom.core.utilities.results.SuccessResult;
import com.gameroom.Gameroom.dataAccess.abstracts.HesapKonukUrunleriDao;
import com.gameroom.Gameroom.dataAccess.abstracts.KonukDao;
import com.gameroom.Gameroom.dataAccess.abstracts.KonukHesaplarDao;
import com.gameroom.Gameroom.dataAccess.abstracts.KonukUrunleriDao;
import com.gameroom.Gameroom.dataAccess.abstracts.UrunlerDao;
import com.gameroom.Gameroom.entities.concretes.HesapMasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;
import com.gameroom.Gameroom.entities.concretes.Konuk;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;
import com.gameroom.Gameroom.entities.concretes.KonukUrunleri;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Masalar;

@Service
public class KonukHesaplarManager implements KonukHesaplarService{
	
	KonukHesaplarDao konukHesaplarDao;
	KonukDao konukDao;
	KonukUrunleriDao konukUrunleriDao;
	UrunlerDao urunlerDao;
	HesapKonukUrunleriDao hesapKonukUrunleriDao;
	
	@Autowired
	public KonukHesaplarManager(KonukHesaplarDao konukHesaplarDao,
	KonukDao konukDao,
	KonukUrunleriDao konukUrunleriDao,
	UrunlerDao urunlerDao,
	HesapKonukUrunleriDao hesapKonukUrunleriDao) {
		this.konukHesaplarDao = konukHesaplarDao;
		this.konukDao = konukDao;
		this.konukUrunleriDao = konukUrunleriDao;
		this.urunlerDao = urunlerDao;
		this.hesapKonukUrunleriDao = hesapKonukUrunleriDao;
	}

	@Override
	public DataResult<List<KonukHesaplar>> tumHesaplariGetir() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<KonukHesaplar>>(konukHesaplarDao.findAll());
	}

	@Override
	public DataResult<List<KonukHesaplar>> konukHesaplariniGetir(int konukID) {
		List<KonukHesaplar> konukHesaplar = konukHesaplarDao.getByHesapKesilenKonuk_KonukID(konukID); 
		return new SuccessDataResult<List<KonukHesaplar>>(konukHesaplar);
	}

	@Override
	public DataResult<List<KonukHesaplar>> tarihtekiButunHesaplariGetir(String tarih) {
		List<KonukHesaplar> sonHesaplar = new ArrayList<KonukHesaplar>();
		List<KonukHesaplar> konukHesaplar = konukHesaplarDao.findAll();
		for (KonukHesaplar hesap : konukHesaplar) {
			int gun = hesap.getKonukHesapKesimSaati().getDayOfMonth();
			int ay = hesap.getKonukHesapKesimSaati().getMonthValue();
			int yil = hesap.getKonukHesapKesimSaati().getYear();
			String tarih2 = String.valueOf(gun)+String.valueOf(ay)+String.valueOf(yil);
			if(tarih.equals(tarih2)) {
				sonHesaplar.add(hesap);
			}
		}
		return new SuccessDataResult<List<KonukHesaplar>>(sonHesaplar);
	}

	@Override
	public DataResult<List<KonukHesaplar>> tarihtekiKonukHesaplariniGetir(String tarih, int konukID) {
		List<KonukHesaplar> sonHesaplar = new ArrayList<KonukHesaplar>();
		List<KonukHesaplar> konukHesaplar = konukHesaplarDao.findAll();
		for (KonukHesaplar hesap : konukHesaplar) {
			int gun = hesap.getKonukHesapKesimSaati().getDayOfMonth();
			int ay = hesap.getKonukHesapKesimSaati().getMonthValue();
			int yil = hesap.getKonukHesapKesimSaati().getYear();
			String tarih2 = String.valueOf(gun)+String.valueOf(ay)+String.valueOf(yil);
			if(tarih.equals(tarih2)) {
				if(hesap.getHesapKesilenKonuk().getKonukID() == konukID) {
					sonHesaplar.add(hesap);
				}
			}
		}
		return new SuccessDataResult<List<KonukHesaplar>>(sonHesaplar);
	}

	@Override
	public DataResult<List<KonukHesaplar>> tarihAraligindakiHesaplariGetir(String baslangicTarihi, String bitisTarihi,
			int konukID) {
		LocalDate baslangic = LocalDate.parse(baslangicTarihi, DateTimeFormatter.ISO_DATE);
		LocalDate bitis = LocalDate.parse(bitisTarihi,DateTimeFormatter.ISO_DATE);
		LocalDateTime baslangicSon = LocalDateTime.of(baslangic, LocalTime.MIN);
		LocalDateTime bitisSon = LocalDateTime.of(bitis, LocalTime.MIN);
		
		List<KonukHesaplar> filtrelenmisHesaplar = konukHesaplarDao.findAll().stream().filter(hesap -> hesap.getKonukHesapKesimSaati().isAfter(baslangicSon)
				&& hesap.getKonukHesapKesimSaati().isBefore(bitisSon)).collect(Collectors.toList());
	
		if(konukID == 9999) {	
			return new SuccessDataResult<List<KonukHesaplar>>(filtrelenmisHesaplar, "Hesaplar getirildi!");
		}
		filtrelenmisHesaplar = filtrelenmisHesaplar.stream().filter(hesap -> hesap.getHesapKesilenKonuk().getKonukID() == konukID).collect(Collectors.toList());
		
		return new SuccessDataResult<List<KonukHesaplar>>(filtrelenmisHesaplar, "Hesaplar getirildi!");
	}

	@Override
	public DataResult<List<KonukHesapUrunleri>> hesapKonukUrunleriniGetir(int konukHesapID) {
		KonukHesaplar konukHesap = konukHesaplarDao.getByKonukHesapID(konukHesapID);
		if(konukHesap == null) {
			return new ErrorDataResult<List<KonukHesapUrunleri>>(false, "Hesap bulunamadı!");
		}
		return new SuccessDataResult<List<KonukHesapUrunleri>>(hesapKonukUrunleriDao.getByKonukHesap_KonukHesapID(konukHesapID));
	}

	@Override
	public Result hesapEkle(int konukID, String hesapOdemeYontemi) {
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorResult(false,"Konuk bulunamadı");
		}
		LocalDateTime time = LocalDateTime.now();
		if(konuk.getKonukAcildigiSaat() == null) {
			return new ErrorResult(false,"Konuk yok!");
		}
		
		float hesapToplami = 0;
		
		KonukHesaplar hesap = new KonukHesaplar();
		KonukHesapUrunleri hesapKonukUrunleri = new KonukHesapUrunleri();
		List<Integer> konukUrunlerininID = new ArrayList<Integer>();
		List<Integer> konukUrunlerininAdedi = new ArrayList<Integer>();
		for (KonukUrunleri konukUrunu: konukUrunleriDao.getByKonuk_KonukID(konukID)) {
			hesapToplami += konukUrunu.getUrun().getUrunFiyati();
			if(konukUrunu.getKonukUrunAdeti() > 1) {
				hesapKonukUrunleri.setKonukUrunAdeti(konukUrunu.getKonukUrunAdeti());
			}
			konukUrunlerininAdedi.add(konukUrunu.getKonukUrunAdeti());
			konukUrunlerininID.add(konukUrunu.getUrun().getUrunID());
			konukUrunleriDao.delete(konukUrunu);
		}
		hesap.setKonukHesapKesimSaati(time);
		hesap.setKonukHesapToplami(hesapToplami);
		hesap.setHesapKesilenKonuk(konuk);
		hesap.setKonukHesapOdemeYontemi(hesapOdemeYontemi);
		konuk.setKonukHesabiToplami(0);
		konuk.setKonukAcildigiSaat(null);
		konuk.setKonukAktif(false);
		konukDao.save(konuk);
		konukHesaplarDao.save(hesap);
		int x = 0;
		for (Integer integer : konukUrunlerininID) {
			KonukHesapUrunleri urun = new KonukHesapUrunleri();
			urun.setKonukHesap(hesap);
			urun.setUrun(urunlerDao.getByUrunID(integer));
			urun.setKonukUrunAdeti(konukUrunlerininAdedi.get(x));
			x++;
			hesapKonukUrunleriDao.save(urun);
		}
		hesapKonukUrunleri.setKonukHesap(hesap);
		return new SuccessResult(true, "Hesap eklendi!");
	}

	@Override
	public Result hesapHesaplama(int konukID) {
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorResult(false,"Konuk bulunamadı");
		}
		double hesapToplami = 0;
		for (KonukUrunleri konukUrunu: konukUrunleriDao.getByKonuk_KonukID(konukID)) {
			hesapToplami += (konukUrunu.getUrun().getUrunFiyati() * konukUrunu.getKonukUrunAdeti());
		}
		if(konuk.getKonukAcildigiSaat() == null) {
			return new ErrorResult(false,"Masa kapalı!");
		}
		return new SuccessResult(true,String.valueOf(hesapToplami));
	}

	@Override
	public Result hesapSil(int konukID) {
		KonukHesaplar konukHesaplar= konukHesaplarDao.getByKonukHesapID(konukID);
		if(konukHesaplar == null) {
			return new ErrorResult(false,"Hesap bulunamadı");
		}
		List<KonukHesapUrunleri> konukHesapUrunleri = hesapKonukUrunleriDao.getByKonukHesap_KonukHesapID(konukID);
		for (KonukHesapUrunleri urun: konukHesapUrunleri) {
			hesapKonukUrunleriDao.delete(urun);
		}
		konukHesaplarDao.delete(konukHesaplar);
		return new SuccessResult(true,"Hesap silindi");
	}

	@Override
	public Result hesapDuzenle(int hesapID, int hesapToplami, String hesapOdemeYontemi) {
		// TODO Auto-generated method stub
		return null;
	}

}
