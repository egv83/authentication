package com.ochobits.authentication.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.ochobits.authentication.utils.LocalDateTimeUtils.getDateTimeFormated;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    @Column(columnDefinition = "BIT(1) default 0",nullable = true)
    private boolean status;

    @PrePersist
    private void prePersist(){
        this.creationDate = getDateTimeFormated(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
    }

    @PreUpdate
    private void preUpdate(){
        this.updateDate = getDateTimeFormated(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
    }

}
