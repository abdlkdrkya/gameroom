package com.gameroom.Gameroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.KisilerService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.KisiUrunleri;
import com.gameroom.Gameroom.entities.concretes.Kisiler;

@RestController
@RequestMapping("api/kisi")
@CrossOrigin
public class KisilerController {

	
	private KisilerService kisilerService;
	
	@Autowired
	public KisilerController(KisilerService kisilerService) {
		super();
		this.kisilerService = kisilerService;
	}
	
	@PostMapping("/kisiEkle")
	public Result kisiEkle(String kisiAdi) {
		return kisilerService.kisiEkle(kisiAdi);
	}
	
	@GetMapping("/butunKisileriGetir")
	public DataResult<List<Kisiler>> butunKisileriGetir(){
		
		return kisilerService.butunKisileriGetir();
	}
	
	@PatchMapping("/kisiHesabiDuzenle")
	public Result kisiHesabiDuzenle(int kisiID, String kisiAdi, String kisiHesabiToplami)  {
		return kisilerService.kisiHesabiDuzenle(kisiID, kisiAdi, kisiHesabiToplami);
	}
	
	@PatchMapping("/kisiSil")
	public Result kisiSil(int kisiID) {
		
		return kisilerService.kisiSil(kisiID);
		
	}
	
	@PostMapping("/kisiUrunEkle")
	public Result kisiUrunEkle(int kisiID, int urunID) {
		return kisilerService.kisiUrunEkle(kisiID, urunID);
	}
	
	@GetMapping("/kisiUrunleriGetir")
	public DataResult<List<KisiUrunleri>> kisiUrunleriGetir(int kisiID){
		return kisilerService.kisiUrunleriGetir(kisiID);
	}
}
