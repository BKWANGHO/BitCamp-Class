package com.jsggun.api.item.domain;

import com.jsggun.api.order.domain.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class ItemModel {

    @Id
    @Column(name = "item_id")
    private long itemId;
    @Column(name = "item_brand")
    private String itemBrand;
    @Column(name = "model_no")
    private String modelNo;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_color")
    private String itemColor;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "sold_out")
    private Boolean soldOut;

    @OneToMany(mappedBy = "item")
    private List<Order> orders = new ArrayList<>();
    @Builder
    public ItemModel(String itemBrand, String itemName, String itemColor){
        this.itemBrand = itemBrand;
        this.itemName = itemName;
        this.itemColor = itemColor;
    }


}