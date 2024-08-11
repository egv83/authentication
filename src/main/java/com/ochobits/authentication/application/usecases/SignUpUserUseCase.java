package com.ochobits.authentication.application.usecases;

import com.ochobits.authentication.application.input.SignUpUserRequest;
import com.ochobits.authentication.domain.exceptions.UserAlreadyExists;
import com.ochobits.authentication.domain.model.User;
import com.ochobits.authentication.domain.ports.PasswordEncoderPort;
import com.ochobits.authentication.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Service;

/*DEFINO COMO SERVICIO PARA QUE LO RECONOSCA SPRING Y NO TENES QUE HACER UN BEAN*/
@Service
public class SignUpUserUseCase {

    /*SE HACE USO DE LOS PUESRTO PARA TENER ACCESO A LA INFORMACION REQUERIDA*/
    private final UserRepositoryPort userRepositoryPort;

    /*SE HACE EL LLAMADO AL PUERTO PARA UTILIZAR LA ENCRIPTACIÓN*/
    private final PasswordEncoderPort passwordEncoderPort;

    public SignUpUserUseCase(
            UserRepositoryPort userRepositoryPort
            , PasswordEncoderPort passwordEncoderPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    /*AQUI SE HACE LA LOGICA DE NEGOCIO*/
    /**
     * 1. VALIDAR SI EL CORREO YA ESTA REGISTRADO, DE SER ASÍ RETORNAR ERROR
     * 2. SI YA ESTA REGISTRADO, RETORNAR ERROR
     * 3. CREAR EL USUARIO Y GUARDAR EL USUARIO EN BDD
     */
    public void execute(SignUpUserRequest request){
        var userOpt = userRepositoryPort.findUserByEmail(request.email());
        if (userOpt.isPresent()){
            throw new UserAlreadyExists("The user with this email already exists");
        }

        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoderPort.encode(request.password()))
                .build();

        userRepositoryPort.save(user);
    }

}
