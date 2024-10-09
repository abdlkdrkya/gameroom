package com.gameroom.Gameroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.GiderlerService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.Giderler;

@RestController
@RequestMapping("api/giderler")
@CrossOrigin
public class GiderlerController {

	private GiderlerService giderlerService;
	
	@Autowired
	public GiderlerController(GiderlerService giderlerService) {
		super();
		this.giderlerService = giderlerService;
	}
	
	@PostMapping("/giderEkle")
	public Result giderEkle(String giderAdi, double giderFiyati) {
		return giderlerService.giderEkle(giderAdi, giderFiyati);
	}
	
	@PatchMapping("/giderSil")
	public Result giderSil(int giderID) {
		return giderlerService.giderSil(giderID);
	}
	
	@PatchMapping("/giderDuzenle")
	public Result giderDuzenle(int giderID, String giderAdi, double giderFiyati) {
		return giderlerService.giderDuzenle(giderID, giderAdi, giderFiyati);
	}
	
	@GetMapping("/giderGetir")
	public DataResult<Giderler> giderGetir(int giderID){
		return giderlerService.giderGetir(giderID);
	}
	
	@GetMapping("/giderlerGetir")
	public DataResult<List<Giderler>> giderlerGetir(){
		return giderlerService.giderlerGetir();
	}
	
	@GetMapping("/aydakiGiderleriGetir")
	public DataResult<List<Giderler>> aydakiGiderleriGetir(String ay){
		return giderlerService.aydakiGiderleriGetir(ay);
	}
	
	@GetMapping("/tarihAraligindakiGiderleriGetir")
	public DataResult<List<Giderler>> tarihAraligindakiGiderleriGetir(String baslangicTarihi, String bitisTarihi) {
		return giderlerService.tarihAraligindakiGiderleriGetir(baslangicTarihi, bitisTarihi);
	}
}
