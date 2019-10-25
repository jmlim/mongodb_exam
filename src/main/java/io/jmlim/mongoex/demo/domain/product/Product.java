package io.jmlim.mongoex.demo.domain.product;


import io.jmlim.mongoex.demo.domain.common.Price;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private ObjectId id;
    private String slug;
    private String sku;
    private String name;
    private String description;
    private Detail details;
    private int totalReviews;
    private double averageReview;
    private Price pricing;
    private List<PriceHistory> priceHistory;
    private ObjectId primaryCategory;
    private List<ObjectId> categoryIds;
    private ObjectId mainCatId;
    private List<String> tags;
}