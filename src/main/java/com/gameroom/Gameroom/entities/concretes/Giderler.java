package com.gameroom.Gameroom.entities.concretes;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "giderler")
public class Giderler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "giderID")
	private int giderID;
	
	@Column(name = "giderAdi")
	private String giderAdi;
	
	@Column(name = "giderFiyati")
	private double giderFiyati;
	
	@Column(name = "giderTarihi")
	private LocalDateTime giderTarihi;

}
