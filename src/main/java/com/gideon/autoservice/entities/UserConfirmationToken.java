package com.gideon.autoservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class UserConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long tokenId;

    private String confirmationToken;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public UserConfirmationToken(User user) {
        this.user = user;
        confirmationToken = UUID.randomUUID().toString();

    }

    public UserConfirmationToken() {
    }

}
