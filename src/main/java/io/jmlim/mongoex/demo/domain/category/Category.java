package io.jmlim.mongoex.demo.domain.category;


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
@Document(collection = "categories")
@ToString
public class Category implements Function<CategoryDto, Category> {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String slug;
    private String name;
    private String description;
    private ObjectId parentId;
    private List<Ancestor> ancestors;

    // valid check 시 dto 에서 처리하도록..
    @Override
    public Category apply(CategoryDto updateCategory) {
        this.name = updateCategory.getName();
        this.description = updateCategory.getDescription();
        if (Objects.nonNull(updateCategory.getParentId())) {
            this.parentId = updateCategory.getParentId();
        }

        if (CollectionUtils.isNotEmpty(updateCategory.getAncestors())) {
            this.ancestors = updateCategory.getAncestors();
        }
        return this;
    }
}