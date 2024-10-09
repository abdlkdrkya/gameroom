package com.gameroom.Gameroom.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.KisilerService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorDataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.core.utilities.results.SuccessDataResult;
import com.gameroom.Gameroom.core.utilities.results.SuccessResult;
import com.gameroom.Gameroom.dataAccess.abstracts.KisiUrunleriDao;
import com.gameroom.Gameroom.dataAccess.abstracts.KisilerDao;
import com.gameroom.Gameroom.dataAccess.abstracts.UrunlerDao;
import com.gameroom.Gameroom.entities.concretes.KisiUrunleri;
import com.gameroom.Gameroom.entities.concretes.Kisiler;
import com.gameroom.Gameroom.entities.concretes.Konuk;
import com.gameroom.Gameroom.entities.concretes.KonukUrunleri;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Urunler;

@Service
public class KisilerManager implements KisilerService{

	private KisilerDao kisilerDao;
	private UrunlerDao urunlerDao;
	private KisiUrunleriDao kisiUrunleriDao;
	
	@Autowired
	public KisilerManager(KisilerDao kisilerDao, UrunlerDao urunlerDao, KisiUrunleriDao kisiUrunleriDao) {
		super();
		this.kisilerDao = kisilerDao;
		this.kisiUrunleriDao = kisiUrunleriDao;
		this.urunlerDao = urunlerDao;
	}

	@Override
	public Result kisiEkle(String kisiAdi) {
		Kisiler kisi = new Kisiler();
		kisi.setKisiAdi(kisiAdi);
		kisi.setKisiHesabiToplami(0);
		kisilerDao.save(kisi);
		return new SuccessResult(true, "Kişi eklendi");
	}

	@Override
	public DataResult<List<Kisiler>> butunKisileriGetir() {
		return new SuccessDataResult<List<Kisiler>>(kisilerDao.findAll());
	}

	@Override
	public Result kisiHesabiDuzenle(int kisiID, String kisiAdi, String kisiHesabiToplami) {
		Kisiler kisi = kisilerDao.getByKisiID(kisiID);
		if(kisi == null) {
			return new ErrorResult(false,"Kişi bulunamadı");
		}
		double x = Double.parseDouble(kisiHesabiToplami);
		kisi.setKisiHesabiToplami(x);
		kisi.setKisiAdi(kisiAdi);
		kisilerDao.save(kisi);
		return new SuccessResult(true, "Fiyat güncellendi");
	}

	@Override
	public Result kisiSil(int kisiID) {
		Kisiler kisi = kisilerDao.getByKisiID(kisiID);
		if(kisi == null) {
			return new ErrorResult(false,"Kişi bulunamadı");
		}
		kisilerDao.delete(kisi);
		return new SuccessResult(true, "Kişi silindi");
	}
	
	@Override
	public Result kisiUrunEkle(int kisiID, int urunID) {
		Kisiler kisi = kisilerDao.getByKisiID(kisiID);
		if(kisi == null) {
			return new ErrorResult(false,"Kişi bulunamadı");
		}
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		KisiUrunleri kisiUrunu = kisiUrunleriDao.getByKisi_KisiIDAndUrun_UrunID(kisiID, urunID);
		if(kisiUrunu == null) {
			KisiUrunleri yeniKisiUrunu = new KisiUrunleri();
			yeniKisiUrunu.setKisi(kisi);
			yeniKisiUrunu.setKisiUrunAdeti(1);
			yeniKisiUrunu.setUrun(urun);
			kisiUrunleriDao.save(yeniKisiUrunu);
			kisi.setKisiHesabiToplami(kisi.getKisiHesabiToplami()+urun.getUrunFiyati());
			kisilerDao.save(kisi);
			return new SuccessResult(true,"Yeni ürün eklendi");
		}
		int urunAdeti = kisiUrunu.getKisiUrunAdeti();
		kisiUrunu.setKisiUrunAdeti(urunAdeti+1);
		kisiUrunleriDao.save(kisiUrunu);
		kisi.setKisiHesabiToplami(kisi.getKisiHesabiToplami()+urun.getUrunFiyati());
		kisilerDao.save(kisi);
		return new SuccessResult(true,"Ürün eklendi");
	}

	@Override
	public DataResult<List<KisiUrunleri>> kisiUrunleriGetir(int kisiID) {
		Kisiler kisi= kisilerDao.getByKisiID(kisiID);
		if(kisi == null) {
			return new ErrorDataResult<>(false, "Konuk bulunamadı!");
		}
		return new SuccessDataResult<List<KisiUrunleri>>(kisiUrunleriDao.getByKisi_KisiID(kisiID));
	}
	
}
