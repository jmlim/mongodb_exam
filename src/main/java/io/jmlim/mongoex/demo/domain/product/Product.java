package io.jmlim.mongoex.demo.domain.product;


import io.jmlim.mongoex.demo.domain.category.Category;
import io.jmlim.mongoex.demo.domain.category.CategoryDto;
import io.jmlim.mongoex.demo.domain.common.Price;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
@ToString
public class Product implements Function<ProductDto, Product> {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String slug;
    private String sku;
    private String name;
    private String description;
    private Detail details;
    private int totalReviews;
    private double averageReview;
    @Setter
    private Price pricing;
    private List<PriceHistory> priceHistory;
    private ObjectId primaryCategory;
    private List<ObjectId> categoryIds;
    private ObjectId mainCatId;
    private List<String> tags;

    @Override
    public Product apply(ProductDto updateProduct) {
        this.slug = updateProduct.getSlug();
        this.sku = updateProduct.getSku();
        this.name = updateProduct.getName();
        this.description = updateProduct.getDescription();
        if (Objects.nonNull(updateProduct.getDetails())) {
            this.details = updateProduct.getDetails();
        }
        this.totalReviews = updateProduct.getTotalReviews();
        this.averageReview = updateProduct.getAverageReview();
        if (Objects.nonNull(updateProduct.getPricing())) {
            this.pricing = updateProduct.getPricing();
        }
        if (CollectionUtils.isNotEmpty(updateProduct.getPriceHistory())) {
            this.priceHistory = updateProduct.getPriceHistory();
        }
        this.primaryCategory = updateProduct.getPrimaryCategory();
        if (CollectionUtils.isNotEmpty(updateProduct.getCategoryIds())) {
            this.categoryIds = updateProduct.getCategoryIds();
        }
        this.mainCatId = updateProduct.getMainCatId();
        if (CollectionUtils.isNotEmpty(updateProduct.getTags())) {
            this.tags = updateProduct.getTags();
        }
        return this;
    }
}