package com.gameroom.Gameroom.entities.concretes;

import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "konuk")
public class Konuk {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "konukID")
	private int konukID;
	
	@Column(name = "konukIsim")
	private String konukIsim;
		
	@Column(name = "konukHesabiToplami")
	private double konukHesabiToplami;
	
	@Column(name = "konukAktif")
	private boolean konukAktif;
	
	@Column(name = "konukAcildigiSaat")
	private LocalDateTime konukAcildigiSaat;
	
	@Column(name = "konukKapandigiSaat")
	private LocalDateTime konukKapandigiSaat;
	
	@JsonIgnore
	@OneToMany(mappedBy = "konuk")
	List<KonukUrunleri> konukUrunleri;
	
}
