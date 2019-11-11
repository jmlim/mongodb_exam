package io.jmlim.mongoex.demo.domain.review;

import io.jmlim.mongoex.demo.domain.common.BaseDto;
import lombok.*;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"voterIds"})
public class ReviewDto implements BaseDto<Review> {

    @NonNull
    private ObjectId id;
    @NonNull
    private ObjectId productId;
    @NonNull
    private Date date;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    private int rating;
    @NonNull
    private ObjectId userId;
    @NotEmpty
    private String username;
    private int helpfulVotes;
    private List<ObjectId> voterIds;

    @Override
    public Review toEntity() {
        return Review.builder()
                .productId(this.productId)
                .date(this.date)
                .title(this.title)
                .text(this.text)
                .rating(this.rating)
                .userId(this.userId)
                .username(this.username)
                .helpfulVotes(this.helpfulVotes)
                .voterIds(this.voterIds)
                .build();
    }
}
