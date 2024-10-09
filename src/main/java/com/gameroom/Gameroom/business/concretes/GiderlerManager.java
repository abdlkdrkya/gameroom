package com.gameroom.Gameroom.business.concretes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gameroom.Gameroom.business.abstracts.GiderlerService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorDataResult;
import com.gameroom.Gameroom.core.utilities.results.ErrorResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.core.utilities.results.SuccessDataResult;
import com.gameroom.Gameroom.core.utilities.results.SuccessResult;
import com.gameroom.Gameroom.dataAccess.abstracts.GiderlerDao;
import com.gameroom.Gameroom.entities.concretes.Giderler;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;

@Service
public class GiderlerManager implements GiderlerService{
	
	private GiderlerDao giderlerDao;
	
	@Autowired
	public GiderlerManager(GiderlerDao giderlerDao) {
		super();
		this.giderlerDao = giderlerDao;
	}

	@Override
	public Result giderEkle(String giderAdi, double giderFiyati) {
		Giderler gider = new Giderler();
		gider.setGiderAdi(giderAdi);
		gider.setGiderFiyati(giderFiyati);
		gider.setGiderTarihi(LocalDateTime.now());
		giderlerDao.save(gider);
		return new SuccessResult(true, "Gider eklendi");
	}

	@Override
	public Result giderSil(int giderID) {
		Giderler gider = giderlerDao.getByGiderID(giderID);
		if(gider == null) {
			return new ErrorResult(false, "Gider bulunamadı!");
		}
		giderlerDao.delete(gider);
		return new SuccessResult(true, "Gider silindi");
	}

	@Override
	public Result giderDuzenle(int giderID, String giderAdi, double giderFiyati) {
		Giderler gider = giderlerDao.getByGiderID(giderID);
		if(gider == null) {
			return new ErrorResult(false, "Gider bulunamadı!");
		}
		if(!giderAdi.equals("null")) {
			gider.setGiderAdi(giderAdi);
		}
		if(giderFiyati != 999) {
			gider.setGiderFiyati(giderFiyati);
		}
		return new SuccessResult(true, "Gider düzenlendi!");
	}

	@Override
	public DataResult<Giderler> giderGetir(int giderID) {
		Giderler gider = giderlerDao.getByGiderID(giderID);
		if(gider == null) {
			return new ErrorDataResult<Giderler>(false, "Gider bulunamadı!");
		}
		return new SuccessDataResult<Giderler>(gider, "Gider getirildi!");
	}

	@Override
	public DataResult<List<Giderler>> giderlerGetir() {
		List<Giderler> giderler = giderlerDao.findAll();
		return new SuccessDataResult<>(giderler, "Giderler getirildi");
	}

	@Override
	public DataResult<List<Giderler>> aydakiGiderleriGetir(String ay) {
		int x = Integer.valueOf(ay);
		List<Giderler> giderler = giderlerDao.findAll();
		
		List<Giderler> aydakiGiderler = giderler.stream()
				.filter(gider -> gider.getGiderTarihi().getMonthValue() == x)
				.collect(Collectors.toList());
		int y = 0;
		for(int i = 0; i < aydakiGiderler.size(); i++) {
			y++;
		}
		return new DataResult<List<Giderler>>(aydakiGiderler, "Aydaki giderler getirildi");
	}

	@Override
	public DataResult<List<Giderler>> tarihAraligindakiGiderleriGetir(String baslangicTarihi, String bitisTarihi) {
		LocalDate baslangic = LocalDate.parse(baslangicTarihi, DateTimeFormatter.ISO_DATE);
		LocalDate bitis = LocalDate.parse(bitisTarihi,DateTimeFormatter.ISO_DATE);
		LocalDateTime baslangicSon = LocalDateTime.of(baslangic, LocalTime.MIN);
		LocalDateTime bitisSon = LocalDateTime.of(bitis, LocalTime.MIN);
		
		List<Giderler> filtrelenmisGiderler = giderlerDao.findAll().stream().filter(gider -> gider
				.getGiderTarihi().isAfter(baslangicSon)
				&& gider.getGiderTarihi().isBefore(bitisSon)).collect(Collectors.toList());
	
		
		return new SuccessDataResult<List<Giderler>>(filtrelenmisGiderler, "Hesaplar getirildi!");
	}

}
