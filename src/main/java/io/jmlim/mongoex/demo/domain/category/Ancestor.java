package io.jmlim.mongoex.demo.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ancestor {
    private String name;
    private ObjectId id;
    private String slug;
}
