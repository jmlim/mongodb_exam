package io.jmlim.mongoex.demo.domain.order;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 *
 _id: ObjectId("6a5b1476238d3b4dd500048"),
 user_id: ObjectId("4c4b1476238d3b4dd5000001"),
 state: "CART",
 line_items: [ // <-- 비정규화된 상품정보
 {
 _id: ObjectId("4c4b1476238d3b4dd5003981"),
 sku: "9092",
 name: "Extra Large Wheelbarrow",
 quantity: 1,
 pricing: {
 retail: 5897,
 sale: 4897
 }
 },
 {
 _id: ObjectId("4c4b1476238d3b4dd5003982"),
 sku: "10027",
 name: "Rubberized Work Glove, Black",
 quantity: 2,
 pricing: {
 retail: 1499,
 sale: 1299
 }
 }
 ],
 shipping_address: {
 street: "588 5th Street",
 city: "Brooklyn",
 state: "NY",
 zip: 11215
 },
 sub_total: 6196  // <-- 비정규화된 세일 가격의 합
 }
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "orders")
public class Order implements Serializable {

    @Id
    @Setter
    private ObjectId id;
    private ObjectId userId;
    private String state;
    private List<LineItem> lineItems;
    private ShippingAddress shippingAddress;
    private int subTotal;
}