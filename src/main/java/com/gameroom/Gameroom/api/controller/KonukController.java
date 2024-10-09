package com.gameroom.Gameroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.KonukService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.Konuk;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;
import com.gameroom.Gameroom.entities.concretes.KonukUrunleri;

@RestController
@RequestMapping("api/konuk")
@CrossOrigin
public class KonukController {
	
private KonukService konukService;
	
	@Autowired
	public KonukController(KonukService konukService) {
		super();
		this.konukService = konukService;
	}
	
	@GetMapping("/tumKonuklariGetir")
	public DataResult<List<Konuk>> butunKonuklariGetir(){
		return konukService.butunAcikKonuklariGetir();
	}
	
	@PostMapping("/konukAc")
	public Result konukEkle(String konukIsim) {
		return konukService.konukEkle(konukIsim);
	}
	
	@PatchMapping("/konukSil")
	public Result konukSil(int konukID) {
		return konukService.konukSil(konukID);
	}
	
	@PostMapping("/konukUrunEkle")
	public Result konukUrunEkle (int konukID, int urunID) {
		return konukService.konukUrunEkle(konukID, urunID);
	}
	
	@GetMapping("/butunKonukUrunleriniGetir")
	public DataResult<List<KonukUrunleri>> butunKonuklarinUrunleriniGetir(){
		return konukService.butunKonuklarinUrunleriniGetir();
	}
	
	@GetMapping("/aydakiTumKonukHesaplariniGetir")
	public DataResult<List<KonukHesaplar>> aydakiTumKonukHesaplariniGetir(String ay) {
		return konukService.aydakiTumKonukHesaplariniGetir(ay);
	}
	
	@PatchMapping("/konukUrunSil")
	public Result konukUrunSil(int konukID, int urunID) {
		return konukService.konukUrunSil(konukID, urunID);
	}
	
	@PatchMapping("/konukIsmiDegistir")
	public Result konukIsimDegistir(int konukID, String konukIsim) {
		return konukService.konukIsimDegistir(konukID, konukIsim);
	}
	
	@GetMapping("/tarihAraligindakiKonuklariGetir")
	public DataResult<List<KonukHesaplar>> tarihAraligindakiKonuklariGetir(String baslangicTarihi, String bitisTarihi){
		return konukService.tarihAraligindakiKonuklariGetir(baslangicTarihi, bitisTarihi);
	}
	
	@GetMapping("/konukHesapUrunleriniGetir")
	public DataResult<List<KonukHesapUrunleri>> konukHesapUrunleriniGetir(int konukID){
		return konukService.konukHesapUrunleriniGetir(konukID);
	}
}
