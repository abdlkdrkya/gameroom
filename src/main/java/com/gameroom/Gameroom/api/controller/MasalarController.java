package com.gameroom.Gameroom.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.MasalarService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Masalar;
import com.gameroom.Gameroom.entities.concretes.Urunler;

@RestController
@RequestMapping("api/masalar")
@CrossOrigin
public class MasalarController {

	private MasalarService masalarService;
	
	@Autowired
	public MasalarController(MasalarService masalarService) {
		super();
		this.masalarService = masalarService;
	}
	
	@GetMapping("/masayigetir")
	public DataResult<Masalar> getByMasaNumarasi(int masaNumarasi){
		return masalarService.getByMasaNumarasi(masaNumarasi);
	}
	
	@GetMapping("/butunmasalarigetir")
	public DataResult<List<Masalar>> butunMasalariGetir() {
		return masalarService.butunMasalariGetir();
	}
	
	@PostMapping("/masaekle")
	public Result masaEkleme(int masaNumarasi, String masaAdi) {
		return masalarService.masaEkleme(masaNumarasi, masaAdi);
	}
	
	@PatchMapping("/masaUrununuSil")
	public Result masaninUrununuSil(int masaID, int urunID) {
		return masalarService.masaninUrununuSil(masaID, urunID);
	}
	
	@PostMapping("/masabilgileridegistir")
	public Result masaBilgileriDegistirme(int masaNumarasi, String masaAdi) {
		return masalarService.masaBilgileriDegistirme(masaNumarasi, masaAdi);
	}
	
	@PatchMapping("/masayaurunekle")
	public Result masayaUrunEkle(int masaID, int urunID) {
		return masalarService.masayaUrunEkle(masaID, urunID);
	}
	
	@GetMapping("/masaurunlerinigetir")
	public DataResult<List<MasaUrunleri>> masaUrunleriniGetir(int masaID){
		return masalarService.masaUrunleriniGetir(masaID);
	}
	
	@PostMapping("/masayiAc")
	public Result masayiAc(int masaID, String masa4Kol) {
		return masalarService.masayiAc(masaID, masa4Kol);
	}
	
	@GetMapping("/masayiKapat")
	public Result masayiKapat(int masaID, String masa4Kol) {
		return masalarService.masayiKapat(masaID, masa4Kol);
	}
	
	@GetMapping("/butunMasalarinUrunleriniGetir")
	public DataResult<List<MasaUrunleri>> butunMasalarinUrunleriniGetir(){
		return masalarService.butunMasalarinUrunleriniGetir();
	}
	
	@PatchMapping("/masayiAktar")
	public Result masayiAktar(int aktarilanMasaID, int aktarilacakMasaID) {
		return masalarService.masayiAktar(aktarilanMasaID, aktarilacakMasaID);
	}
}
