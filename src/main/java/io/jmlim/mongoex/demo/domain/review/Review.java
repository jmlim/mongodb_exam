package io.jmlim.mongoex.demo.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reviews")
public class Review {

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
}
