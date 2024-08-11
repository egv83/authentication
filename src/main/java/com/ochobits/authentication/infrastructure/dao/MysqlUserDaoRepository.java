package com.ochobits.authentication.infrastructure.dao;

import com.ochobits.authentication.domain.model.User;
import com.ochobits.authentication.domain.ports.UserRepositoryPort;
import com.ochobits.authentication.infrastructure.entities.UserEntity;
import com.ochobits.authentication.infrastructure.repositories.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*ESTA CLASE SERA EL ADAPTADOR EN CUAL IMPLEMENTA AL PUERO MEDIANTE UN IMPLEMENTS*/
/* IMPLEMENTA EL PUERTO QUE ESTA EN DOMMAIN*/
@Repository
public class MysqlUserDaoRepository implements UserRepositoryPort {

    /*LLAMO AL REPOSITORIO QUE ES LA COMUNICACION ENTRE LA ENTIDAD Y LA BDD*/
    /*LAS INTERFACES SON LOS PUESRTOR */
    private final JpaUserRepository jpaUserRepository;

    public MysqlUserDaoRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    /*SE IMPLEMETA EL METODO DEFINIDO EN LAINTERFACE, LLAMANDO AL MODELO QUE ESTA EN DOMAIN/MODEL*/
    @Override
    public Optional<User> findById(UUID id) {
        var entityOpt = jpaUserRepository.findById(id);
        if (entityOpt.isEmpty()){
            return Optional.empty();
        }

        var entity = entityOpt.get();
        var user = User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .build();

        return Optional.of(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        var userEntity = jpaUserRepository.findByEmail(email);
        if (userEntity.isEmpty()){
            return Optional.empty();
        }

        var entity = userEntity.get();
        var user = User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .status(entity.isStatus()?"Active":"Inactive")
                .build();
        return Optional.of(user);

    }

    @Override
    public void save(User user) {
        var entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .status(true)
                .build();
        jpaUserRepository.save(entity);
    }

}
