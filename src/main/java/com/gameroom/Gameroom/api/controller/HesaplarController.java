package com.gameroom.Gameroom.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gameroom.Gameroom.business.abstracts.HesaplarService;
import com.gameroom.Gameroom.business.abstracts.KonukHesaplarService;
import com.gameroom.Gameroom.core.utilities.results.DataResult;
import com.gameroom.Gameroom.core.utilities.results.Result;
import com.gameroom.Gameroom.entities.concretes.HesapMasaUrunleri;
import com.gameroom.Gameroom.entities.concretes.Hesaplar;
import com.gameroom.Gameroom.entities.concretes.KonukHesapUrunleri;
import com.gameroom.Gameroom.entities.concretes.KonukHesaplar;
import com.gameroom.Gameroom.entities.concretes.MasaUrunleri;

@RestController
@RequestMapping("api/hesaplar")
@CrossOrigin
public class HesaplarController {

	private HesaplarService hesaplarService;
	
	@Autowired
	public HesaplarController(HesaplarService hesaplarService){
		super();
		this.hesaplarService = hesaplarService;
	}
	
	@PostMapping("/hesapekle")
	public Result hesapEkle(int masaID, @RequestParam String hesapOdemeYontemi) {
		return hesaplarService.hesapEkle(masaID, hesapOdemeYontemi);
	}
	
	@GetMapping("/hesapHesapla")
	public Result hesapHesaplama(int masaID) {
		return hesaplarService.hesapHesaplama(masaID);
	}
	
	@PatchMapping("/hesapsil")
	public Result hesapSil(int hesapID) {
		return hesaplarService.hesapSil(hesapID);
	}
	
	@GetMapping("/hesaplarigetir")
	public DataResult<List<Hesaplar>> tumHesaplariGetir(){
		return hesaplarService.tumHesaplariGetir();
	}
	
	@GetMapping("/masahesaplarigetir")
	public DataResult<List<Hesaplar>> masaHesaplariniGetir(int masaID){
		return hesaplarService.masaHesaplariniGetir(masaID);
	}
	
	@GetMapping("/tarihtekibutunhesaplarigetir")
	public DataResult<List<Hesaplar>> tarihtekiButunHesaplariGetir(String tarih){
		return hesaplarService.tarihtekiButunHesaplariGetir(tarih);
	}
	
	@GetMapping("/tarihtekimasaninhesaplarigetir")
	public DataResult<List<Hesaplar>> tarihtekiMasaninHesaplariGetir(String tarih, int masaID){
		return hesaplarService.tarihtekiMasaninHesaplariGetir(tarih, masaID);
	}
	
	@GetMapping("/tariharaligindakihesaplarigetir")
	public DataResult<List<Hesaplar>> tarihAraligindakiHesaplariGetir(String baslangicTarihi,String bitisTarihi, int masaID){
		return hesaplarService.tarihAraligindakiHesaplariGetir(baslangicTarihi, bitisTarihi, masaID);
	}

	@GetMapping("/tariharaligindakihesapurunlerinigetir")
	public DataResult<List<HesapMasaUrunleri>> tarihAraligindakiHesapUrunleriniGetir(String baslangicTarihi,String bitisTarihi, int masaID){
		return hesaplarService.tarihAraligindakiHesapUrunleriniGetir(baslangicTarihi, bitisTarihi, masaID);
	}
	
	@GetMapping("/hesapMasaUrunleriniGetir")
	public DataResult<List<HesapMasaUrunleri>> hesapMasaUrunleriniGetir(int hesapID){
		return hesaplarService.hesapMasaUrunleriniGetir(hesapID);
	}

	
	@PatchMapping("/hesapDuzenle")
	public Result hesapDuzenle(int hesapID, int hesapToplami, String hesapOdemeYontemi) {
		return hesaplarService.hesapDuzenle(hesapID, hesapToplami, hesapOdemeYontemi);
	}
	
	@GetMapping("/aydakiHesaplariGetir")
	public DataResult<List<Hesaplar>> aydakiTumHesaplariGetir(String ay){
		return hesaplarService.aydakiTumHesaplariGetir(ay);
	}
	
	@GetMapping("/aydakiTumHesapUrunleriGetir")
	public DataResult<List<HesapMasaUrunleri>> aydakiTumHesapUrunleriGetir(String ay){
		return hesaplarService.aydakiTumHesapUrunleriGetir(ay);
	}
}
