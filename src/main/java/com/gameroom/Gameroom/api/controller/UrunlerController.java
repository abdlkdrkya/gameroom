package com.gameroom.Gameroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.UrunlerService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.Urunler;

@RestController
@RequestMapping("api/urunler")
@CrossOrigin
public class UrunlerController {

	private UrunlerService urunlerService;
	
	@Autowired
	public UrunlerController(UrunlerService urunlerService) {
		super();
		this.urunlerService = urunlerService;
	}
	
	@PostMapping("/urunekle")
	public Result urunEkle(String urunAdi, double urunFiyati, double urunGelisFiyati, String urunCinsi) {
		return urunlerService.urunEkle(urunAdi, urunFiyati, urunGelisFiyati, urunCinsi);
	}
	
	@PatchMapping("/urunsil")
	public Result urunSil(int urunID) {
		return urunlerService.urunSil(urunID);
	}
	
	@GetMapping("/butunurunlerigetir")
	public DataResult<List<Urunler>> butunUrunleriGetir(){
		return urunlerService.butunUrunleriGetir();
	}
	
	@GetMapping("/urungetir")
	public DataResult<Urunler> getByUrunID(int urunID){
		return urunlerService.getByUrunID(urunID);
	}
	
	@PatchMapping("/urunduzenle")
	public Result urunDuzenle(int urunID, String urunAdi, String urunFiyati) {
		return urunlerService.urunDuzenle(urunID, urunAdi, urunFiyati);
	}
	
	@PatchMapping("/urunstokduzenle")
	public Result urunStokDuzenle(int urunID, int urunAdeti) {
		return urunlerService.urunStokDuzenle(urunID, urunAdeti);
	}
	
	@PatchMapping("/urunstokekle")
	public Result urunStokEkle(int urunID, int urunAdeti) {
		return urunlerService.urunStokEkle(urunID, urunAdeti);
	}
}
