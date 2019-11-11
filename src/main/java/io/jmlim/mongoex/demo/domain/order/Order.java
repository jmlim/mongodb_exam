package io.jmlim.mongoex.demo.domain.order;

import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "orders")
@ToString
public class Order implements Function<OrderDto, Order> {

    @Id
    private ObjectId id;
    private ObjectId userId;
    private String state;
    private List<LineItem> lineItems;
    private ShippingAddress shippingAddress;
    private int subTotal;

    @Override
    public Order apply(OrderDto updateOrder) {
        this.userId = updateOrder.getUserId();
        this.state = updateOrder.getState();
        if (CollectionUtils.isNotEmpty(updateOrder.getLineItems())) {
            this.lineItems = updateOrder.getLineItems();
        }
        if (Objects.nonNull(updateOrder.getShippingAddress())) {
            this.shippingAddress = updateOrder.getShippingAddress();
        }
        return this;
    }
}