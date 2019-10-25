package io.jmlim.mongoex.demo.domain.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddress {
    private String street;
    private String city;
    private String state;
    private int zip;
}
