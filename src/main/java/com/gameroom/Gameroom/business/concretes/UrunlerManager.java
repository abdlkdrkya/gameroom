package com.gameroom.Gameroom.business.concretes;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.UrunlerService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorDataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.core.utilities.results.SuccessDataResult;
import com.gameroom.Gameroom.core.utilities.results.SuccessResult;
import com.gameroom.Gameroom.dataAccess.abstracts.UrunlerDao;
import com.gameroom.Gameroom.entities.concretes.Urunler;

@Service
public class UrunlerManager implements UrunlerService{
	
	private UrunlerDao urunlerDao;
	
	@Autowired
	public UrunlerManager(UrunlerDao urunlerDao) {
		super();
		this.urunlerDao = urunlerDao;
	}

	@Override
	public DataResult<List<Urunler>> butunUrunleriGetir() {
		List<Urunler> urunler = urunlerDao.findAll();
		return new SuccessDataResult<List<Urunler>>(urunlerDao.findAll());
	}

	@Override
	public DataResult<Urunler> getByUrunID(int urunID) {
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorDataResult<Urunler>(false, "Ürün bulunamadı");
		}
		return new SuccessDataResult<Urunler>(urun);
	}

	@Override
	public Result urunEkle(String urunAdi, double urunFiyati, double urunGelisFiyati, String urunCinsi) {
		Urunler urun = new Urunler();
		urun.setUrunAdi(urunAdi);
		urun.setUrunFiyati(urunFiyati);
		urun.setUrunGelisFiyati(urunGelisFiyati);
		urun.setUrunCinsi(urunCinsi);
		urunlerDao.save(urun);
		return new SuccessResult(true, "Ürün eklendi");
	}

	@Override
	public Result urunDuzenle(int urunID, String urunAdi, String urunFiyati) {
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		double x = Float.valueOf(urunFiyati);
		urun.setUrunFiyati(x);
		urun.setUrunAdi(urunAdi);
		urunlerDao.save(urun);
		return new SuccessResult(true, "Ürün fiyatı değiştirildi");
	}

	@Override
	public Result urunSil(int urunID) {
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		urunlerDao.delete(urun);
		return new SuccessResult(true,"Ürün silindi");
	}

	@Override
	public Result urunStokDuzenle(int urunID, int urunAdeti) {
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		urun.setUrunAdeti(urunAdeti);
		urunlerDao.save(urun);
		return new SuccessResult(true, "Stok düzenlendi!");
	}

	@Override
	public Result urunStokEkle(int urunID, int urunAdeti) {
		Urunler urun = urunlerDao.getByUrunID(urunID);
		if(urun == null) {
			return new ErrorResult(false,"Ürün bulunamadı");
		}
		urun.setUrunAdeti(urun.getUrunAdeti()+urunAdeti);
		urunlerDao.save(urun);
		return new SuccessResult(true, "Stok eklendi!");
	}
	

}
