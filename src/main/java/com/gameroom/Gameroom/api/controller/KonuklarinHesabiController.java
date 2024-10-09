package com.gameroom.Gameroom.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.KonukHesaplarService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;


@RestController
@RequestMapping("api/konukHesaplari")
@CrossOrigin
public class KonuklarinHesabiController {
	
	private KonukHesaplarService konukHesaplarService;
	
	@Autowired
	public KonuklarinHesabiController (KonukHesaplarService konukHesaplarService) {
		super();
		this.konukHesaplarService = konukHesaplarService;
	}
	
	@PostMapping("/konukhesapekle")
	public Result konukHesapEkle(int konukID, @RequestParam String hesapOdemeYontemi) {
		return konukHesaplarService.hesapEkle(konukID, hesapOdemeYontemi);
	}
	
	@GetMapping("/konukHesapHesapla")
	public Result konukHesapHesaplama(int konukID) {
		return konukHesaplarService.hesapHesaplama(konukID);
	}
	
	@PatchMapping("/hesapsil")
	public Result konukHesapSil(int hesapID) {
		return konukHesaplarService.hesapSil(hesapID);
	}
	
	@GetMapping("/hesaplarigetir")
	public DataResult<List<KonukHesaplar>> tumKonukHesaplariGetir(){
		return konukHesaplarService.tumHesaplariGetir();
	}
	
	@GetMapping("/konukhesaplarigetir")
	public DataResult<List<KonukHesaplar>> konukHesaplariniGetir(int konukID){
		return konukHesaplarService.konukHesaplariniGetir(konukID);
	}
	
	@GetMapping("/tarihtekibutunkonukhesaplarigetir")
	public DataResult<List<KonukHesaplar>> tarihtekiButunKonukHesaplariniGetir(String tarih, int konukID){
		return konukHesaplarService.tarihtekiKonukHesaplariniGetir(tarih, konukID);
	}
	
	@GetMapping("/tarihtekikonukhesaplarigetir")
	public DataResult<List<KonukHesaplar>> tarihtekiKonukHesaplariGetir(String tarih, int masaID){
		return konukHesaplarService.tarihtekiKonukHesaplariniGetir(tarih, masaID);
	}
	
	@GetMapping("/tariharaligindakikonukhesaplarigetir")
	public DataResult<List<KonukHesaplar>> tarihAraligindakiKonukHesaplariGetir(String baslangicTarihi,String bitisTarihi, int masaID){
		return konukHesaplarService.tarihAraligindakiHesaplariGetir(baslangicTarihi, bitisTarihi, masaID);
	}
	
	@GetMapping("/hesapKonukUrunleriniGetir")
	public DataResult<List<KonukHesapUrunleri>> hesapKonukUrunleriniGetir(int konukID){
		return konukHesaplarService.hesapKonukUrunleriniGetir(konukID);
	}
	
	@PatchMapping("/konukHesapDuzenle")
	public Result konukHesapDuzenle(int konukID, int hesapToplami, String hesapOdemeYontemi) {
		return konukHesaplarService.hesapDuzenle(konukID, hesapToplami, hesapOdemeYontemi);
	}
}
