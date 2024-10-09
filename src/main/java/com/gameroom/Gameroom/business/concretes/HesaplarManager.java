package com.gameroom.Gameroom.business.concretes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.HesaplarService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorDataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.core.utilities.results.SuccessDataResult;
import com.gameroom.Gameroom.core.utilities.results.SuccessResult;
import com.gameroom.Gameroom.dataAccess.abstracts.HesapMasaUrunleriDao;
import com.gameroom.Gameroom.dataAccess.abstracts.HesaplarDao;
import com.gameroom.Gameroom.dataAccess.abstracts.MasaUrunleriDao;
import com.gameroom.Gameroom.dataAccess.abstracts.MasalarDao;
import com.gameroom.Gameroom.dataAccess.abstracts.UrunlerDao;
import com.gameroom.Gameroom.entities.concretes.HesapMasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Masalar;

@Service
public class HesaplarManager implements HesaplarService{

	HesaplarDao hesaplarDao;
	MasalarDao masalarDao;
	MasaUrunleriDao masaUrunleriDao;
	UrunlerDao urunlerDao;
	HesapMasaUrunleriDao hesapMasaUrunleriDao;
	
	@Autowired
	public HesaplarManager(HesaplarDao hesaplarDao, MasalarDao masalarDao,
			MasaUrunleriDao masaUrunleriDao, HesapMasaUrunleriDao hesapMasaUrunleriDao,
			UrunlerDao urunlerDao) {
		this.hesaplarDao = hesaplarDao;
		this.masalarDao = masalarDao;
		this.masaUrunleriDao = masaUrunleriDao;
		this.urunlerDao = urunlerDao;
		this.hesapMasaUrunleriDao = hesapMasaUrunleriDao;
	}
	
	@Override
	public Result hesapHesaplama(int masaID) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false,"Masa bulunamadı");
		}
		
		double hesapToplami = 0;
		for (MasaUrunleri masaUrunu: masaUrunleriDao.getByMasa_MasaID(masaID)) {
			hesapToplami += (masaUrunu.getUrun().getUrunFiyati() * masaUrunu.getMasaUrunAdeti());
		}
		LocalDateTime kalkisZamani = LocalDateTime.now();
		if(masa.getMasaninAcildigiSaat() == null) {
			return new ErrorResult(false,"Masa kapalı!");
		}
		Duration gecenSure = Duration.between(masa.getMasaninAcildigiSaat(), kalkisZamani);
		int gecenSaat = (int) ( gecenSure.toHours() % 24L );
		long sayi = gecenSure.toMinutes();
		int gecenDakika  = (int)(sayi % 60L);
		
		if(masa.getMasaID() == 4) {
			
			if(gecenSaat == 0  && gecenDakika < 60) {
				if(masa.isMasa4Kol()) {
					hesapToplami += 200;
				}
				else {
					hesapToplami += 100;
				}
				
			}
			else {
				if(masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*200;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 100;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 200;
					}
				}
				else if(!masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*100;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 50;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 100;
					}
				}
			}
		}
		
		else {
			if(gecenSaat == 0  && gecenDakika < 60) {
				if(masa.isMasa4Kol()) {
					hesapToplami += 140;
				}
				else {
					hesapToplami += 70;
				}
				
			}
			else {
				if(masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*140;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 70;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 140;
					}
				}
				else if(!masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*70;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 35;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 70;
					}
				}
			}
		}
		
		

		return new SuccessResult(true,String.valueOf(hesapToplami));
	}
	
	@Override
	public Result hesapEkle(int masaID, String hesapOdemeYontemi) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false,"Masa bulunamadı");
		}
		LocalDateTime kalkisZamani = LocalDateTime.now(); 
		LocalDateTime time = LocalDateTime.now();
		if(masa.getMasaninAcildigiSaat() == null) {
			return new ErrorResult(false,"Masa kapalı!");
		}
		
		double hesapToplami = 0;
		
		Hesaplar hesap = new Hesaplar();
		List<Integer> masaUrunlerininID = new ArrayList<Integer>();
		List<Integer> masaUrunlerininAdedi = new ArrayList<Integer>();
		for (MasaUrunleri masaUrunu: masaUrunleriDao.getByMasa_MasaID(masaID)) {
			hesapToplami += (masaUrunu.getUrun().getUrunFiyati() * masaUrunu.getMasaUrunAdeti());
			masaUrunlerininAdedi.add(masaUrunu.getMasaUrunAdeti());
			masaUrunlerininID.add(masaUrunu.getUrun().getUrunID());
			masaUrunleriDao.delete(masaUrunu);
		}
		Duration gecenSure = Duration.between(masa.getMasaninAcildigiSaat(), kalkisZamani);
		int gecenSaat = (int) ( gecenSure.toHours() % 24L );
		long sayi = gecenSure.toMinutes();
		int gecenDakika  = (int)(sayi % 60L);
		
if(masa.getMasaID() == 4) {
			
			if(gecenSaat == 0  && gecenDakika < 60) {
				if(masa.isMasa4Kol()) {
					hesapToplami += 200;
				}
				else {
					hesapToplami += 100;
				}
				
			}
			else {
				if(masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*200;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 100;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 200;
					}
				}
				else if(!masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*100;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 50;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 100;
					}
				}
			}
		}
		
		else {
			if(gecenSaat == 0  && gecenDakika < 60) {
				if(masa.isMasa4Kol()) {
					hesapToplami += 140;
				}
				else {
					hesapToplami += 70;
				}
				
			}
			else {
				if(masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*140;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 70;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 140;
					}
				}
				else if(!masa.isMasa4Kol()) {
					hesapToplami += gecenSaat*70;
					if(gecenDakika > 0 && gecenDakika <= 30 ) {
						hesapToplami += 35;
					}
					else if(gecenDakika > 30 && gecenDakika < 60) {
						hesapToplami += 70;
					}
				}
			}
		}
		
		hesap.setHesapKesimSaati(time);
		hesap.setHesapToplami(hesapToplami);
		hesap.setHesapKesilenMasa(masa);
		hesap.setHesapOdemeYontemi(hesapOdemeYontemi);
		masa.setMasaUcreti(0);
		masa.setMasaninAcildigiSaat(null);
		masa.setMasaAcik(false);
		masalarDao.save(masa);
		hesaplarDao.save(hesap);
		int x = 0;
		for (Integer integer : masaUrunlerininID) {
			HesapMasaUrunleri urun = new HesapMasaUrunleri();
			urun.setHesap(hesap);
			urun.setUrun(urunlerDao.getByUrunID(integer));
			urun.setMasaUrunAdeti(masaUrunlerininAdedi.get(x));
			x++;
			hesapMasaUrunleriDao.save(urun);
		}
		return new SuccessResult(true, "Hesap eklendi!");
	}
	
	@Override
	public DataResult<List<HesapMasaUrunleri>> hesapMasaUrunleriniGetir(int hesapID){
		Hesaplar hesap = hesaplarDao.getByHesapID(hesapID);
		if(hesap == null) {
			return new ErrorDataResult<List<HesapMasaUrunleri>>(false, "Hesap bulunamadı!");
		}
		return new SuccessDataResult<List<HesapMasaUrunleri>>(hesapMasaUrunleriDao.getByHesap_HesapID(hesapID));
	}

	@Override
	public Result hesapSil(int hesapID) {
		Hesaplar hesap = hesaplarDao.getByHesapID(hesapID);
		if(hesap == null) {
			return new ErrorResult(false,"Hesap bulunamadı");
		}
		List<HesapMasaUrunleri> hesapMasaUrunleri = hesapMasaUrunleriDao.getByHesap_HesapID(hesapID);
		for (HesapMasaUrunleri urun: hesapMasaUrunleri) {
			hesapMasaUrunleriDao.delete(urun);
		}
		hesaplarDao.delete(hesap);
		return new SuccessResult(true,"Hesap silindi");
	}

	@Override
	public DataResult<List<Hesaplar>> tumHesaplariGetir() {
		return new SuccessDataResult<List<Hesaplar>>(hesaplarDao.findAll());
	}
	
	

	@Override
	public DataResult<List<Hesaplar>> masaHesaplariniGetir(int masaID) {
		List<Hesaplar> hesaplar = hesaplarDao.getByHesapKesilenMasa_MasaID(masaID);
		return new SuccessDataResult<List<Hesaplar>>(hesaplar);
	}

	@Override
	public DataResult<List<Hesaplar>> tarihtekiButunHesaplariGetir(String tarih) {
		List<Hesaplar> sonHesaplar = new ArrayList<Hesaplar>();
		List<Hesaplar> hesaplar = hesaplarDao.findAll();
		for (Hesaplar hesap : hesaplar) {
			int gun = hesap.getHesapKesimSaati().getDayOfMonth();
			int ay = hesap.getHesapKesimSaati().getMonthValue();
			int yil = hesap.getHesapKesimSaati().getYear();
			String tarih2 = String.valueOf(gun)+String.valueOf(ay)+String.valueOf(yil);
			if(tarih.equals(tarih2)) {
				sonHesaplar.add(hesap);
			}
		}
		return new SuccessDataResult<List<Hesaplar>>(sonHesaplar);
	}

	@Override
	public DataResult<List<Hesaplar>> tarihtekiMasaninHesaplariGetir(String tarih, int masaID) {
		List<Hesaplar> sonHesaplar = new ArrayList<Hesaplar>();
		List<Hesaplar> hesaplar = hesaplarDao.findAll();
		for (Hesaplar hesap : hesaplar) {
			int gun = hesap.getHesapKesimSaati().getDayOfMonth();
			int ay = hesap.getHesapKesimSaati().getMonthValue();
			int yil = hesap.getHesapKesimSaati().getYear();
			String tarih2 = String.valueOf(gun)+String.valueOf(ay)+String.valueOf(yil);
			if(tarih.equals(tarih2)) {
				if(hesap.getHesapKesilenMasa().getMasaID() == masaID) {
					sonHesaplar.add(hesap);
				}
			}
		}
		return new SuccessDataResult<List<Hesaplar>>(sonHesaplar);
	}
	
	@Override
	public DataResult<List<Hesaplar>> tarihAraligindakiHesaplariGetir(String baslangicTarihi,String bitisTarihi, int masaID){
		LocalDate baslangic = LocalDate.parse(baslangicTarihi, DateTimeFormatter.ISO_DATE);
		LocalDate bitis = LocalDate.parse(bitisTarihi,DateTimeFormatter.ISO_DATE);
		LocalDateTime baslangicSon = LocalDateTime.of(baslangic, LocalTime.MIN);
		LocalDateTime bitisSon = LocalDateTime.of(bitis, LocalTime.MIN);
		
		List<Hesaplar> filtrelenmisHesaplar = hesaplarDao.findAll().stream().filter(hesap -> hesap.getHesapKesimSaati().isAfter(baslangicSon)
				&& hesap.getHesapKesimSaati().isBefore(bitisSon)).collect(Collectors.toList());
	
		if(masaID == 9999) {	
			return new SuccessDataResult<List<Hesaplar>>(filtrelenmisHesaplar, "Hesaplar getirildi!");
		}
		filtrelenmisHesaplar = filtrelenmisHesaplar.stream().filter(hesap -> hesap.getHesapKesilenMasa().getMasaID() == masaID).collect(Collectors.toList());
		
		return new SuccessDataResult<List<Hesaplar>>(filtrelenmisHesaplar, "Hesaplar getirildi!");
	}
	
	@Override
	public DataResult<List<HesapMasaUrunleri>> tarihAraligindakiHesapUrunleriniGetir(String baslangicTarihi,String bitisTarihi, int masaID) {
		 LocalDate baslangic = LocalDate.parse(baslangicTarihi, DateTimeFormatter.ISO_DATE);
		    LocalDate bitis = LocalDate.parse(bitisTarihi, DateTimeFormatter.ISO_DATE);
		    LocalDateTime baslangicSon = LocalDateTime.of(baslangic, LocalTime.MIN);
		    LocalDateTime bitisSon = LocalDateTime.of(bitis, LocalTime.MAX);

		    // Tüm Hesaplar varlıklarını çek ve belirtilen tarih aralığında filtrele
		    List<Hesaplar> filtrelenmisHesaplar = hesaplarDao.findAll().stream()
		        .filter(hesap -> hesap.getHesapKesimSaati().isAfter(baslangicSon)
		                      && hesap.getHesapKesimSaati().isBefore(bitisSon))
		        .collect(Collectors.toList());

		    // MasaID 9999 ise tüm masaların ürünlerini döndür
		    if (masaID == 9999) {
		        List<HesapMasaUrunleri> tumHesapUrunleri = filtrelenmisHesaplar.stream()
		            .flatMap(hesap -> hesap.getHesapMasaUrunleri().stream())
		            .collect(Collectors.toList());
		        return new SuccessDataResult<>(tumHesapUrunleri, "Tarih aralığındaki tüm masaların hesap ürünleri getirildi!");
		    }

		    // Belirtilen masaID'ye sahip hesapların ürünlerini döndür
		    List<HesapMasaUrunleri> belirliMasaUrunleri = filtrelenmisHesaplar.stream()
		        .filter(hesap -> hesap.getHesapKesilenMasa().getMasaID() == masaID)
		        .flatMap(hesap -> hesap.getHesapMasaUrunleri().stream())
		        .collect(Collectors.toList());

		    return new SuccessDataResult<>(belirliMasaUrunleri, "Tarih aralığındaki belirtilen masanın hesap ürünleri getirildi!");
		
	}
	@Override
	public Result hesapDuzenle(int hesapID, int hesapToplami, String hesapOdemeYontemi) {
		Hesaplar hesap = hesaplarDao.getByHesapID(hesapID);
		if(hesap == null) {
			return new ErrorResult(false,"Hesap bulunamadı!");
		}
		hesap.setHesapToplami(hesapToplami);
		hesap.setHesapOdemeYontemi(hesapOdemeYontemi);
		hesaplarDao.save(hesap);
		return new SuccessResult(true, "Hesap düzenlendi!");
	}

	@Override
	public DataResult<List<Hesaplar>> aydakiTumHesaplariGetir(String ay) {
		
		int x = Integer.valueOf(ay);
		List<Hesaplar> hesaplar = hesaplarDao.findAll();
		
		List<Hesaplar> hesaplarAydaki = hesaplar.stream()
	            .filter(hesap -> hesap.getHesapKesimSaati().getMonthValue() == x) 
	            .collect(Collectors.toList());
		int y = 0;
		for (int i = 0; i < hesaplarAydaki.size(); i++) {
			y++;
		}
	    return new DataResult<List<Hesaplar>>(hesaplarAydaki, "Aydaki hesaplar başarıyla getirildi.");
		
	}
	
	public DataResult<List<HesapMasaUrunleri>> aydakiTumHesapUrunleriGetir(String ay){
		 int x;
		    try {
		        x = Integer.parseInt(ay);
		        if (x < 1 || x > 12) {
		            return new DataResult<>(new ArrayList<>(), "Geçersiz ay değeri. Ay 1 ile 12 arasında olmalıdır.");
		        }
		    } catch (NumberFormatException e) {
		        return new DataResult<>(new ArrayList<>(), "Ay değeri geçerli bir sayı olmalıdır.");
		    }

		    // Tüm Hesaplar varlıklarını çek
		    List<Hesaplar> hesaplar = hesaplarDao.findAll();

		    // Belirtilen ay içindeki HesapMasaUrunleri'ni filtrele ve topla
		    List<HesapMasaUrunleri> hesapUrunleri = hesaplar.stream()
		            .filter(hesap -> hesap.getHesapKesimSaati().getMonthValue() == x)
		            .flatMap(hesap -> hesap.getHesapMasaUrunleri().stream())
		            .collect(Collectors.toList());

		    // Sonucu döndür
		    return new DataResult<>(hesapUrunleri, "Aydaki hesaplar başarıyla getirildi.");
	}

	@Override
	public DataResult<List<Hesaplar>> tarihAyHesaplariGetir(int ay, int masaID) {
		// TODO Auto-generated method stub
		return null;
	}

}
