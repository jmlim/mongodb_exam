package io.jmlim.mongoex.demo.domain.user;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {
    private String name;
    private String paymentToken;
}
