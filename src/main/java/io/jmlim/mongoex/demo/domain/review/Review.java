package io.jmlim.mongoex.demo.domain.review;

import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reviews")
@ToString
public class Review implements Function<ReviewDto, Review> {

    @Id
    private ObjectId id;
    private ObjectId productId;
    private Date date;
    private String title;
    private String text;
    private int rating;
    private ObjectId userId;
    private String username;
    private int helpfulVotes;
    private List<ObjectId> voterIds;

    @Override
    public Review apply(ReviewDto updateReview) {
        this.id = updateReview.getId();
        this.productId = updateReview.getProductId();
        this.date = updateReview.getDate();
        this.title = updateReview.getTitle();
        this.text = updateReview.getText();
        this.rating = updateReview.getRating();
        this.userId = updateReview.getUserId();
        this.username = updateReview.getUsername();
        this.helpfulVotes = updateReview.getHelpfulVotes();
        if (CollectionUtils.isNotEmpty(updateReview.getVoterIds())) {
            this.voterIds = updateReview.getVoterIds();
        }
        return this;
    }
}
