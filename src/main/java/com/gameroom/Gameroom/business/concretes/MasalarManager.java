package com.gameroom.Gameroom.business.concretes;



import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.MasalarService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorDataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.core.utilities.results.SuccessDataResult;
import com.gameroom.Gameroom.core.utilities.results.SuccessResult;
import com.gameroom.Gameroom.dataAccess.abstracts.MasaUrunleriDao;
import com.gameroom.Gameroom.dataAccess.abstracts.MasalarDao;
import com.gameroom.Gameroom.dataAccess.abstracts.UrunlerDao;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Masalar;
import com.gameroom.Gameroom.entities.concretes.Urunler;

@Service
public class MasalarManager implements MasalarService{

	private MasalarDao masalarDao;
	private UrunlerDao urunlerDao;
	private MasaUrunleriDao masaUrunleriDao;
	
	@Autowired
	public MasalarManager(MasalarDao masalarDao, UrunlerDao urunlerDao,
			MasaUrunleriDao masaUrunleriDao) {
		super();
		this.masalarDao = masalarDao;
		this.urunlerDao = urunlerDao;
		this.masaUrunleriDao = masaUrunleriDao;
	}
	
	@Override
	public DataResult<Masalar> getByMasaNumarasi(int masaNumarasi){
		Masalar masa = this.masalarDao.getByMasaNumarasi(masaNumarasi);
		if(masa == null) {
			return new ErrorDataResult<Masalar>(false, "Masa bulunamadı");
		}
		return new SuccessDataResult<Masalar>(masa);
	}

	@Override
	public DataResult<List<Masalar>> butunMasalariGetir() {
		Pageable page = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "masaNumarasi"));
		return new SuccessDataResult<List<Masalar>>(this.masalarDao.butunMasalariGetir(page));
	}

	@Override
	public Result masaEkleme(int masaNumarasi, String masaAdi) {
		Masalar masa = new Masalar();
		masa.setMasaAdi(masaAdi);
		masa.setMasaNumarasi(masaNumarasi);
		this.masalarDao.save(masa);
		return new SuccessResult(true);
	}

	@Override
	public Result masaBilgileriDegistirme(int masaNumarasi, String masaAdi) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaNumarasi);
		if(masa == null) {
			return new ErrorResult(false,"Masa bulunamadı");
		}
		masa.setMasaAdi(masaAdi);
		return new SuccessResult(true);
	}

	@Override
	public Result masayaUrunEkle(int masaID, int urunID) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false,"Masa bulunamadı");
		}
		
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		
		if(urun.getUrunAdeti() <= 0) {
			return new ErrorResult(false,"Stokta ürün yok!");
		}
		
		MasaUrunleri masaUrunu = masaUrunleriDao.getByMasa_MasaIDAndUrun_UrunID(masaID, urunID);
		urun.setUrunAdeti(urun.getUrunAdeti()-1);
		urunlerDao.save(urun);
		if(masaUrunu == null) {
			MasaUrunleri yeniMasaUrunu = new MasaUrunleri();
			yeniMasaUrunu.setMasa(masa);
			yeniMasaUrunu.setMasaUrunAdeti(1);
			yeniMasaUrunu.setUrun(urun);
			masaUrunleriDao.save(yeniMasaUrunu);
			masa.setMasaUcreti(masa.getMasaUcreti()+urun.getUrunFiyati());
			masalarDao.save(masa);
			return new SuccessResult(true,"Yeni ürün eklendi");
		}
		
	
		
		int urunAdeti = masaUrunu.getMasaUrunAdeti();
		masaUrunu.setMasaUrunAdeti(urunAdeti+1);
		masaUrunleriDao.save(masaUrunu);
		masa.setMasaUcreti(masa.getMasaUcreti()+urun.getUrunFiyati());
		masalarDao.save(masa);
		return new SuccessResult(true,"Ürün eklendi");
	}
	
	@Override
	public Result masaninUrununuSil(int masaID, int urunID) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false,"Masa bulunamadı");
		}
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		MasaUrunleri masaUrunu = masaUrunleriDao.getByMasa_MasaIDAndUrun_UrunID(masaID, urunID);
		if(masaUrunu == null) {
			return new ErrorResult(false, "Masada ürün bulunamadı!");
		}
		if(masaUrunu.getMasaUrunAdeti() == 1) {
			double masaUcreti = masa.getMasaUcreti();
			masaUcreti -= masaUrunu.getUrun().getUrunFiyati();
			masa.setMasaUcreti(masaUcreti);
			urun.setUrunAdeti(urun.getUrunAdeti()+1);
			urunlerDao.save(urun);
			masaUrunleriDao.delete(masaUrunu);
			masalarDao.save(masa);
			return new SuccessResult(true, "Ürün silindi!");
		}
		else if(masaUrunu.getMasaUrunAdeti() > 1){
			int urunAdeti = masaUrunu.getMasaUrunAdeti();
			masaUrunu.setMasaUrunAdeti(urunAdeti-1);
			double masaUcreti = masa.getMasaUcreti();
			masaUcreti -= masaUrunu.getUrun().getUrunFiyati();
			masa.setMasaUcreti(masaUcreti);
			urun.setUrunAdeti(urun.getUrunAdeti()+1);
			urunlerDao.save(urun);
			masalarDao.save(masa);
			masaUrunleriDao.save(masaUrunu);
			return new SuccessResult(true, "Ürün adeti silindi!");
		}
		return new SuccessResult(true, "Ürün silinemedi!");
	}

	@Override
	public DataResult<List<MasaUrunleri>> masaUrunleriniGetir(int masaID) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorDataResult<>(false, "Masa bulunamadı");
		}
		return new SuccessDataResult<List<MasaUrunleri>>(masaUrunleriDao.getByMasa_MasaID(masaID));
	}

	@Override
	public Result masayiAc(int masaID, String masa4Kol) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false, "Masa bulunamadı");
		}
		if(masa4Kol.equals("evet")) {
			masa.setMasa4Kol(true);
		}
		else {
			masa.setMasa4Kol(false);
		}
		masa.setMasaninAcildigiSaat(LocalDateTime.now());
		masa.setMasaAcik(true);
		masalarDao.save(masa);
		return new SuccessResult(true, "Masa açıldı");
	}

	@Override
	public Result masayiKapat(int masaID, String masa4Kol) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false, "Masa bulunamadı");
		}
		LocalDateTime kalkisZamani = LocalDateTime.now();
		Duration gecenSure = Duration.between(masa.getMasaninAcildigiSaat(), kalkisZamani);
		long gecenSaat = gecenSure.toHours();
		if(gecenSaat <= 0) {
			gecenSaat = 1;
		}
		masa.setMasaAcik(false);
		masalarDao.save(masa);
		return new SuccessResult(true, String.valueOf(gecenSaat));
	}

	@Override
	public DataResult<List<MasaUrunleri>> butunMasalarinUrunleriniGetir() {
		return new SuccessDataResult<List<MasaUrunleri>>(masaUrunleriDao.findAll());
	}
	
	@Override
	public Result masayiAktar(int aktarilanMasaID, int aktarilacakMasaID) {
		Masalar aktarilanMasa = masalarDao.getByMasaNumarasi(aktarilanMasaID);
		if(aktarilanMasa == null) {
			return new ErrorResult(false,"Aktarılan masa bulunamadı!");
		}
		Masalar aktarilacakMasa = masalarDao.getByMasaNumarasi(aktarilacakMasaID);
		if(aktarilacakMasa == null){
			return new ErrorResult(false,"Aktarılacak masa bulunamadı !");
		}
		
		Set<MasaUrunleri> aktarilacakMasaUrunleri = new HashSet<>();
		
		for (MasaUrunleri masaUrunu: masaUrunleriDao.getByMasa_MasaID(aktarilanMasaID)) {
			MasaUrunleri urun = new MasaUrunleri();
			urun.setMasa(aktarilacakMasa);
			urun.setMasaUrunAdeti(masaUrunu.getMasaUrunAdeti());
			urun.setUrun(masaUrunu.getUrun());
			masaUrunleriDao.save(urun);
			masaUrunleriDao.delete(masaUrunu);
		}
		
		aktarilacakMasa.setMasaAcik(true);
		aktarilacakMasa.setMasaninAcildigiSaat(aktarilanMasa.getMasaninAcildigiSaat());
		aktarilacakMasa.setMasaUcreti(aktarilanMasa.getMasaUcreti());
		aktarilacakMasa.setMasaUrunleri(aktarilacakMasaUrunleri);
		masalarDao.save(aktarilacakMasa);
		
		aktarilanMasa.setMasaAcik(false);
		aktarilanMasa.setMasaninAcildigiSaat(null);
		aktarilanMasa.setMasaUcreti(0);
		masalarDao.save(aktarilanMasa);
		
		return new SuccessResult(true, "Masa aktarıldı!");
	}

	@Override
	public Result masaSaatiDuzenle(int masaID, String saat, String dakika) {
		Masalar masa = masalarDao.getByMasaNumarasi(masaID);
		if(masa == null) {
			return new ErrorResult(false,"Masa bulunamadı!");
		}
		
		LocalDateTime zaman = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 
				LocalDateTime.now().getDayOfMonth(), Integer.valueOf(saat), Integer.valueOf(dakika));
		masa.setMasaninAcildigiSaat(zaman);
		masalarDao.save(masa);
		return new SuccessResult(true,"Masa saati değiştirildi!");
	}

}
