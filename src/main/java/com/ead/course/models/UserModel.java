package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String email;


    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false)
    private String userStatus;

    @Column(nullable = false)
    private String userType;


    @Column(length = 255)
    private String imageUrl;

}
