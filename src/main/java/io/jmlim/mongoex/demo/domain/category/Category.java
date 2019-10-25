package io.jmlim.mongoex.demo.domain.category;


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
@Document(collection = "categories")
public class Category implements Serializable {

    @Id
    private ObjectId id;
    private String slug;
    private String name;
    private String description;

    private ObjectId parentId;
    private List<Ancestor> ancestors;
}