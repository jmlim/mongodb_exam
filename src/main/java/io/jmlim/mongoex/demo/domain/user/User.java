package io.jmlim.mongoex.demo.domain.user;


import io.jmlim.mongoex.demo.domain.common.Price;
import io.jmlim.mongoex.demo.domain.product.Detail;
import io.jmlim.mongoex.demo.domain.product.PriceHistory;
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
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private ObjectId id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String hashPassword;
    private List<Address> address;
    private List<PaymentMethod> paymentMethod;
}