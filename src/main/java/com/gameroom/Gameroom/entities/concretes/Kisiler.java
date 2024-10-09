package com.gameroom.Gameroom.entities.concretes;

import java.util.List;

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
@Table(name = "kisiler")
public class Kisiler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kisiID")
	private int kisiID;
	
	@Column(name = "kisiAdi")
	private String kisiAdi;
	
	@Column(name = "kisiHesabiToplami")
	private double kisiHesabiToplami;
	
	@JsonIgnore
	@OneToMany(mappedBy = "kisi")
	List<KisiUrunleri> kisiUrunleri;

}
