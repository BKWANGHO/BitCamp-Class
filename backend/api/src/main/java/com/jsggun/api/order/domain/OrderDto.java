package com.jsggun.api.order.domain;

import com.jsggun.api.item.domain.ItemModel;
import com.jsggun.api.user.model.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;



@Data
@Component
public class OrderDto {
    private long orderId;

    private long price;

    private String count;

    private UserModel user;

    private ItemModel item;

}
