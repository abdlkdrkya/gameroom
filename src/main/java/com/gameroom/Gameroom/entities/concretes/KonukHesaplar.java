package com.gameroom.Gameroom.entities.concretes;

import java.time.LocalDateTime;
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
@Table(name = "konukHesaplar")
public class KonukHesaplar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "konukHesapID")
	private int konukHesapID;
	
	@Column(name = "konukHesapKesimSaati")
	private LocalDateTime konukHesapKesimSaati;
	
	@Column(name = "konukHesapToplami")
	private double konukHesapToplami;
	
	@Column(name = "konukHesapOdemeYontemi")
	private String konukHesapOdemeYontemi;
	
	@ManyToOne
	@JoinColumn(name = "hesapKesilenKonuk")
	private Konuk hesapKesilenKonuk;
	
}
