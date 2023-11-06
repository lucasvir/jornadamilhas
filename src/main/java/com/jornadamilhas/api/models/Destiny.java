package com.jornadamilhas.api.models;

import com.jornadamilhas.api.dto.destiny.DestinyCreateDto;
import com.jornadamilhas.api.dto.destiny.DestinyUpdateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Destiny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imgUrl;
    private BigDecimal price;

    public Destiny() {

    }

    public Destiny(Long id, String name, String imgUrl, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Destiny(DestinyCreateDto dto) {
        this.name = dto.name();
        this.imgUrl = dto.imgUrl();
        this.price = new BigDecimal(dto.price());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destiny destiny = (Destiny) o;
        return Objects.equals(id, destiny.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Destiny{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                '}';
    }

    public void updateData(DestinyUpdateDto dto) {
        this.name = dto.name() != null ? dto.name() : getName();
        this.imgUrl = dto.imgUrl() != null ? dto.imgUrl() : getImgUrl();
        this.price = dto.price() != null ? new BigDecimal(dto.price()) : getPrice();
    }
}
