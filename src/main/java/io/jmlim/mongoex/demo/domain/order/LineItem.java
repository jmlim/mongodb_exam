package io.jmlim.mongoex.demo.domain.order;


import io.jmlim.mongoex.demo.domain.common.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineItem {

    private ObjectId id;
    private String sku;
    private String name;
    private int quantity;
    private Price pricing;
}
