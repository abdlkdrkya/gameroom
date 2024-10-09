  package com.gameroom.Gameroom.business.concretes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.KonukService;
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
import com.gameroom.Gameroom.entities.concretes.Giderler;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;
import com.gameroom.Gameroom.entities.concretes.Konuk;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;
import com.gameroom.Gameroom.entities.concretes.KonukUrunleri;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Urunler;


@Service
public class KonukManager implements KonukService{

	private KonukDao konukDao;
	private UrunlerDao urunlerDao;
	private KonukUrunleriDao konukUrunleriDao;
	private KonukHesaplarDao konukHesaplarDao;
	private HesapKonukUrunleriDao hesapKonukUrunleriDao;
	
	@Autowired
	public KonukManager(KonukDao konukDao, UrunlerDao urunlerDao,KonukUrunleriDao konukUrunleriDao,
			KonukHesaplarDao konukHesaplarDao, HesapKonukUrunleriDao hesapKonukUrunleriDao) {
		super();
		this.konukDao = konukDao;
		this.urunlerDao = urunlerDao;
		this.konukUrunleriDao = konukUrunleriDao;
		this.konukHesaplarDao = konukHesaplarDao;
		this.hesapKonukUrunleriDao = hesapKonukUrunleriDao;
	}

	@Override
	public DataResult<List<Konuk>> butunAcikKonuklariGetir() {
		
		 // Tüm konukları veritabanından al
	    List<Konuk> tumKonuklar = konukDao.findAll();
	    // Aktif konukları saklamak için bir liste oluştur
	    List<Konuk> aktifKonuklar = new ArrayList<Konuk>();
	    
	    // Tüm konuklar arasında dolaşarak sadece aktif olanları ekle
	    for (Konuk konuk : tumKonuklar) {
	        if(konuk.isKonukAktif()) {
	            aktifKonuklar.add(konuk);
	        }
	    }
	    
	    // Aktif konuklar listesini ters çevir
	    Collections.reverse(aktifKonuklar);
	    
	    // Başarılı sonuç olarak aktif konukları döndür
	    return new SuccessDataResult<List<Konuk>>(aktifKonuklar);
		
	}

	@Override
	public Result konukEkle(String konukIsım) {
		
		Konuk konuk = new Konuk();
		LocalDateTime now = LocalDateTime.now();
		
		konuk.setKonukAktif(true);
		konuk.setKonukIsim(konukIsım);
		konuk.setKonukHesabiToplami(0);
		konuk.setKonukAcildigiSaat(now);
		
		konukDao.save(konuk);
		return new SuccessResult(true, "Konuk açıldı!");
	}
	
	@Override
	public Result konukIsimDegistir(int konukID, String konukIsim) {
		
		Konuk konuk = konukDao.getByKonukID(konukID);
		
		if(konuk == null) {
			return new ErrorResult(false,"Konuk bulunamadı!");
		}
		
		konuk.setKonukIsim(konukIsim);
		
		konukDao.save(konuk);
		
		return new SuccessResult(true, "Konuk ismi değiştirildi!");
	}
	
	@Override
	public Result konukSil(int konukID) {
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorResult(false,"Konuk bulunamadı");
		}
		konukDao.delete(konuk);
		return new SuccessResult(true,"Konuk silindi");
	}

	@Override
	public Result konukUrunEkle(int konukID, int urunID) {
		
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorResult(false,"Konuk bulunamadı");
		}
		
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		if(urun.getUrunAdeti() <= 0){
			return new ErrorResult(false,"Stokta ürün yok!");
		}
		
		KonukUrunleri konukUrunu = konukUrunleriDao.getByKonuk_KonukIDAndUrun_UrunID(konukID, urunID);
		urun.setUrunAdeti(urun.getUrunAdeti()-1);
		urunlerDao.save(urun);
		if(konukUrunu == null) {
			KonukUrunleri yeniKonukUrunu = new KonukUrunleri();
			yeniKonukUrunu.setKonuk(konuk);
			yeniKonukUrunu.setUrun(urun);
			yeniKonukUrunu.setKonukUrunAdeti(1);
			konukUrunleriDao.save(yeniKonukUrunu);
			konuk.setKonukHesabiToplami(konuk.getKonukHesabiToplami()+urun.getUrunFiyati());
			konukDao.save(konuk);
			return new SuccessResult(true,"Yeni ürün eklendi");
		}
		int urunAdeti = konukUrunu.getKonukUrunAdeti();
		konukUrunu.setKonukUrunAdeti(urunAdeti+1);
		konukUrunleriDao.save(konukUrunu);
		konuk.setKonukHesabiToplami(konuk.getKonukHesabiToplami()+urun.getUrunFiyati());
		konukDao.save(konuk);
		return new SuccessResult(true, "Ürün eklendi");
	}
	
	@Override
	public DataResult<List<KonukHesaplar>> aydakiTumKonukHesaplariniGetir(String ay) {
		
		int x = Integer.valueOf(ay);
		List<KonukHesaplar> hesaplar = konukHesaplarDao.findAll();
		
		List<KonukHesaplar> hesaplarAydaki = hesaplar.stream()
	            .filter(hesap -> hesap.getKonukHesapKesimSaati().getMonthValue() == x) 
	            .collect(Collectors.toList());
		int y = 0;
		for (int i = 0; i < hesaplarAydaki.size(); i++) {
			y++;
		}
	    return new DataResult<List<KonukHesaplar>>(hesaplarAydaki, "Aydaki hesaplar başarıyla getirildi.");
		
	}

	@Override
	public Result konukUrunSil(int konukID, int urunID) {
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorResult(false,"Konuk bulunamadı");
		}
		
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		
		KonukUrunleri konukUrunu = konukUrunleriDao.getByKonuk_KonukIDAndUrun_UrunID(konukID, urunID);
		if(konukUrunu == null) {
			return new SuccessResult(true,"Konuk ürünü bulunamadı");
		}
		if(konukUrunu.getKonukUrunAdeti() == 1) {
			double konukUcreti = konuk.getKonukHesabiToplami();
			konukUcreti -= konukUrunu.getUrun().getUrunFiyati();
			urun.setUrunAdeti(urun.getUrunAdeti()+1);
			konuk.setKonukHesabiToplami(konukUcreti);
			urunlerDao.save(urun);
			konukUrunleriDao.delete(konukUrunu);
			konukDao.save(konuk);
			return new SuccessResult(true, "Ürün silindi");
		}
		else if(konukUrunu.getKonukUrunAdeti() > 1) {
			int urunAdeti = konukUrunu.getKonukUrunAdeti();
			konukUrunu.setKonukUrunAdeti(urunAdeti-1);
			double konukUcreti = konuk.getKonukHesabiToplami();
			konukUcreti -= konukUrunu.getUrun().getUrunFiyati();
			konuk.setKonukHesabiToplami(konukUcreti);
			urun.setUrunAdeti(urun.getUrunAdeti()+1);
			urunlerDao.save(urun);
			konukDao.save(konuk);
			konukUrunleriDao.save(konukUrunu);
			return new SuccessResult(true, "Ürün adeti silindi!");
		}
		return new ErrorResult(false,"Ürün silinemedi!");
	}
	
	@Override
	public DataResult<List<KonukUrunleri>> konukUrunleriniGetir(int konukID){
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorDataResult<>(false, "Konuk bulunamadı!");
		}
		return new SuccessDataResult<List<KonukUrunleri>>(konukUrunleriDao.getByKonuk_KonukID(konukID));
	}
	
	@Override
	public DataResult<List<KonukUrunleri>> butunKonuklarinUrunleriniGetir() {
		return new SuccessDataResult<List<KonukUrunleri>>(konukUrunleriDao.findAll());
	}

	@Override
	public DataResult<List<KonukHesaplar>> tarihAraligindakiKonuklariGetir(String baslangicTarihi, String bitisTarihi) {
		LocalDate baslangic = LocalDate.parse(baslangicTarihi, DateTimeFormatter.ISO_DATE);
		LocalDate bitis = LocalDate.parse(bitisTarihi,DateTimeFormatter.ISO_DATE);
		LocalDateTime baslangicSon = LocalDateTime.of(baslangic, LocalTime.MIN);
		LocalDateTime bitisSon = LocalDateTime.of(bitis, LocalTime.MIN);
		
		List<KonukHesaplar> filtrelenmisHesaplar = konukHesaplarDao.findAll().stream().filter(hesap -> hesap
				.getKonukHesapKesimSaati().isAfter(baslangicSon)
				&& hesap.getKonukHesapKesimSaati().isBefore(bitisSon)).collect(Collectors.toList());
	
		
		return new SuccessDataResult<List<KonukHesaplar>>(filtrelenmisHesaplar, "Hesaplar getirildi!");
	}

	@Override
	public DataResult<List<KonukHesapUrunleri>> konukHesapUrunleriniGetir(int konukID) {
		Konuk konuk = konukDao.getByKonukID(konukID);
		if(konuk == null) {
			return new ErrorDataResult<>(false, "Konuk bulunamadı!");
		}
		return new SuccessDataResult<List<KonukHesapUrunleri>>(hesapKonukUrunleriDao.getByKonukHesap_KonukHesapID(konukID));
	}
	
}
