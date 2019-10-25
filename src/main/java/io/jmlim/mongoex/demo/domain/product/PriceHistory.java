package io.jmlim.mongoex.demo.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory {
    private long retail;
    private long sale;
    private Date start;
    private Date end;
}
