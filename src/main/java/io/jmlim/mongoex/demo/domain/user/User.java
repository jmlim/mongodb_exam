package io.jmlim.mongoex.demo.domain.user;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.function.Function;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
@ToString
public class User implements Function<UserDto, User> {

    @Id
    private ObjectId id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String hashPassword;
    private List<Address> address;
    private List<PaymentMethod> paymentMethod;

    @Override
    public User apply(UserDto updateUser) {
        this.id = updateUser.getId();
        this.username = updateUser.getUsername();
        this.email = updateUser.getEmail();
        this.firstName = updateUser.getFirstName();
        this.lastName = updateUser.getLastName();
        this.hashPassword = updateUser.getHashPassword();
        this.address = updateUser.getAddress();
        this.paymentMethod = updateUser.getPaymentMethod();
        return this;
    }
}