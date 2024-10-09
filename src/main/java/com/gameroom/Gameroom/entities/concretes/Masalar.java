package com.gameroom.Gameroom.entities.concretes;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "masalar")
public class Masalar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "masaID")
	private int masaID;

	@Column(name = "masaNumarasi")
	private int masaNumarasi;
	
	@Column(name = "masaAdi")
	private String masaAdi;
	
	@Column(name = "masaUcreti")
	private double masaUcreti;
	
	@Column(name = "masaAcik")
	private boolean masaAcik;
	
	@Column(name = "masa4Kol")
	private boolean masa4Kol;
	
	@Column(name = "masaninAcildigiSaat")
	private LocalDateTime masaninAcildigiSaat;
	
	@Column(name = "masaninKapandigiSaat")
	private LocalDateTime masaninKapandigiSaat;
	
	@JsonIgnore
	@OneToMany(mappedBy = "hesapKesilenMasa")
	Set<Hesaplar> hesap;
	
	@JsonIgnore
	@OneToMany(mappedBy = "masa")
	Set<MasaUrunleri> masaUrunleri;
}
