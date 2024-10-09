package com.gameroom.Gameroom.entities.concretes;

import java.time.LocalDateTime;
import java.util.Objects;
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
@Table(name = "hesaplar")
public class Hesaplar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hesapID")
	private int hesapID;
	
	@Column(name = "hesapKesimSaati")
	private LocalDateTime hesapKesimSaati;
	
	@Column(name = "hesapToplami")
	private double hesapToplami;
	
	@Column(name = "hesapOdemeYontemi")
	private String hesapOdemeYontemi;
	
	@ManyToOne
	@JoinColumn(name = "hesapKesilenMasa")
	private Masalar hesapKesilenMasa;
	
	@JsonIgnore
	@OneToMany(mappedBy = "hesap")
	Set<HesapMasaUrunleri> hesapMasaUrunleri;
	
	 @Override
	    public int hashCode() {
	        return Objects.hash(hesapID, hesapKesimSaati);
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Hesaplar hesaplar = (Hesaplar) o;
	        return hesapID == hesaplar.hesapID && Objects.equals(hesapKesimSaati, hesaplar.hesapKesimSaati);
	    }

}
