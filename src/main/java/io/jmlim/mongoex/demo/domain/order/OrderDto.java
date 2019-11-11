package io.jmlim.mongoex.demo.domain.order;


import io.jmlim.mongoex.demo.domain.category.Category;
import io.jmlim.mongoex.demo.domain.common.BaseDto;
import lombok.*;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "lineItems")
public class OrderDto implements BaseDto<Order> {

    @NonNull
    private ObjectId id;
    @NotNull
    private ObjectId userId;
    @NotEmpty
    private String state;
    private List<LineItem> lineItems;
    private ShippingAddress shippingAddress;
    @NotNull
    @PositiveOrZero
    private int subTotal;

    @Override
    public Order toEntity() {
        return Order.builder()
                .userId(this.userId)
                .state(this.state)
                .lineItems(this.lineItems)
                .shippingAddress(this.shippingAddress)
                .subTotal(this.subTotal)
                .build();
    }
}