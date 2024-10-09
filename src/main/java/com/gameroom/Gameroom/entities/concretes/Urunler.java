package com.gameroom.Gameroom.entities.concretes;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urunler")
public class Urunler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "urunID")
	private int urunID;
	
	@Column(name = "urunAdi")
	private String urunAdi;
	
	@Column(name = "urunGelisFiyati")
	private double urunGelisFiyati;
	
	@Column(name = "urunFiyati")
	private double urunFiyati;
	
	@Column(name = "urunCinsi")
	private String urunCinsi;
	
	@Column(name = "urunFotograf")
	private String urunFotograf;
	
	@Column(name = "urunAdeti")
	private int urunAdeti;
	
	@JsonIgnore
	@OneToMany(mappedBy = "urun")
	Set<MasaUrunleri> masaUrunleri;
	
	@JsonIgnore
	@OneToMany(mappedBy = "urun")
	Set<HesapMasaUrunleri> hesapMasaUrunleri;
	
	@JsonIgnore
	@OneToMany(mappedBy = "urun")
	Set<KonukHesapUrunleri> konukHesapUrunleri;
	
	@JsonIgnore
	@OneToMany(mappedBy = "urun")
	List<KonukUrunleri> konukUrunleri;
	
	@JsonIgnore
	@OneToMany(mappedBy = "urun")
	List<KisiUrunleri> kisiUrunleri;

}
