package com.gameroom.Gameroom.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "masaUrunleri")
public class MasaUrunleri {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "masaUrunleriID")
	private int masaUrunleriID;

	@ManyToOne
	@JoinColumn(name = "masa")
	private Masalar masa;
	
	@ManyToOne
	@JoinColumn(name = "urun")
	private Urunler urun;
	
	@Column(name = "masaUrunAdeti")
	private int masaUrunAdeti;
	

	
}
