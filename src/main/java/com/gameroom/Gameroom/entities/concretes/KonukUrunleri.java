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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "konukUrunleri")
public class KonukUrunleri {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "konukUrunleriID")
	private int konukUrunleriID;
	
	@ManyToOne
	@JoinColumn(name = "konuk")
	private Konuk konuk;
	
	@ManyToOne
	@JoinColumn(name = "urun")
	private Urunler urun;
	
	@Column(name = "konukUrunAdeti")
	private int konukUrunAdeti;
}
