package io.jmlim.mongoex.demo.domain.user;


import io.jmlim.mongoex.demo.domain.common.BaseDto;
import lombok.*;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto implements BaseDto<User> {

    @NonNull
    private ObjectId id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String hashPassword;
    private List<Address> address;
    private List<PaymentMethod> paymentMethod;

    @Override
    public User toEntity() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .hashPassword(this.hashPassword)
                .address(this.address)
                .paymentMethod(this.paymentMethod)
                .build();
    }
}