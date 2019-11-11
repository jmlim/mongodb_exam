package io.jmlim.mongoex.demo.domain.product;


import io.jmlim.mongoex.demo.domain.common.BaseDto;
import io.jmlim.mongoex.demo.domain.common.Price;
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
@ToString(exclude = {"priceHistory", "categoryIds", "tags"})
public class ProductDto implements BaseDto<Product> {
    @NotEmpty
    private String slug;
    @NotEmpty
    private String sku;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private Detail details;
    @NotNull
    @PositiveOrZero
    private int totalReviews;
    private double averageReview;
    private Price pricing;
    private List<PriceHistory> priceHistory;
    @NotNull
    private ObjectId primaryCategory;
    private List<ObjectId> categoryIds;
    @NotNull
    private ObjectId mainCatId;
    private List<String> tags;

    @Override
    public Product toEntity() {
        return Product.builder()
                .slug(this.slug)
                .sku(this.sku)
                .name(this.name)
                .description(this.description)
                .details(this.details)
                .totalReviews(this.totalReviews)
                .averageReview(this.averageReview)
                .pricing(this.pricing)
                .priceHistory(this.priceHistory)
                .primaryCategory(this.primaryCategory)
                .categoryIds(this.categoryIds)
                .mainCatId(this.mainCatId)
                .tags(this.tags)
                .build();
    }
}