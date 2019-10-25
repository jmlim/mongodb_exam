package io.jmlim.mongoex.demo.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detail {
    private int weight;
    private String weightUnits;
    private long modelNum;
    private String manufacturer;
    private String color;
}
