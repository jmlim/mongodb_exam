package io.jmlim.mongoex.demo.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = 2227773434535771945L;

    @Id
    @Setter
    private String id;

    private String username;

    private String email;
}