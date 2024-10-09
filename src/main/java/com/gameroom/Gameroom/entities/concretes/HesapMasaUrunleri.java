package com.gameroom.Gameroom.entities.concretes;

import java.util.Objects;

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
@Table(name = "hesapUrunleri")
public class HesapMasaUrunleri {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hesapMasaUrunleriID")
	private int hesapMasaUrunleriID;
	
	@ManyToOne
	@JoinColumn(name = "hesaplar")
	private Hesaplar hesap;
	
	@ManyToOne
	@JoinColumn(name = "urun")
	private Urunler urun;
	
	@Column(name = "masaUrunAdeti")
	private int masaUrunAdeti;
	
	  @Override
	    public int hashCode() {
	        return Objects.hash(hesapMasaUrunleriID, masaUrunAdeti);
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        HesapMasaUrunleri urun = (HesapMasaUrunleri) o;
	        return hesapMasaUrunleriID == urun.hesapMasaUrunleriID && masaUrunAdeti == urun.masaUrunAdeti;
	    }
}
