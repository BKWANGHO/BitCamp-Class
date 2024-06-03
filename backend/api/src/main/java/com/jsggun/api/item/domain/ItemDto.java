package com.jsggun.api.item.domain;

import com.jsggun.api.order.domain.OrderModel;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Data
@Component
public class ItemDto {

    private long itemId;

    private String itemBrand;

    private String modelNo;

    private String itemName;

    private String itemColor;

    private String releaseDate;

    private Boolean soldOut;

    private List<OrderModel> orders = new ArrayList<>();
}
