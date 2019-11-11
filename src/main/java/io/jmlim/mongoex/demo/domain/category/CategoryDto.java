package io.jmlim.mongoex.demo.domain.category;

import io.jmlim.mongoex.demo.domain.common.BaseDto;
import lombok.*;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "ancestors")
public class CategoryDto implements BaseDto<Category> {

    @NotEmpty
    private String slug;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private ObjectId parentId;
    private List<Ancestor> ancestors;

    @Override
    public Category toEntity() {
        return Category.builder()
                .slug(this.slug)
                .name(this.name)
                .description(this.description)
                .parentId(this.parentId)
                .ancestors(this.ancestors)
                .build();
    }
}