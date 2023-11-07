package com.jornadamilhas.api.models;

import com.jornadamilhas.api.dto.destiny.DestinyCreateDto;
import com.jornadamilhas.api.dto.destiny.DestinyUpdateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Destiny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private List<String> imgs;
    private BigDecimal price;

    private String meta;
    @Size(max = 450)
    private String description;

    public Destiny() {

    }

    public Destiny(Long id, String name, List<String> imgs, BigDecimal price, String meta, String description) {
        this.id = id;
        this.name = name;
        this.imgs = imgs;
        this.price = price;
        this.meta = meta;
        this.description = description;
    }

    public Destiny(DestinyCreateDto dto) {
        this.name = dto.name();
        this.imgs = listImgsFromString(dto.imgs());
        this.price = new BigDecimal(dto.price());
        this.meta = dto.meta();
        this.description = dto.description();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMeta() {
        return meta;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", imgs=" + imgs +
                ", price=" + price +
                ", meta='" + meta + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void updateData(DestinyUpdateDto dto) {
        this.name = dto.name() != null ? dto.name() : getName();
        this.imgs = dto.imgs() != null ? listImgsFromString(dto.imgs()) : getImgs();
        this.price = dto.price() != null ? new BigDecimal(dto.price()) : getPrice();
        this.description = dto.description() != null ? dto.description() : getDescription();
        this.meta = dto.meta() != null ? dto.meta() : getMeta();
    }

    private List<String> listImgsFromString(String s) {
        return Arrays.stream(s.split(",")).toList();
    }
}
