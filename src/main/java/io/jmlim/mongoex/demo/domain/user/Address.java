package io.jmlim.mongoex.demo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String name;
    private String street;
    private String city;
    private String state;
    private int zip;
}
