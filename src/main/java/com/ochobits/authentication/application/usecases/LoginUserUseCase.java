package com.ochobits.authentication.application.usecases;

import com.ochobits.authentication.application.input.LoginUserRequest;
import com.ochobits.authentication.application.output.LoginUserResponse;
import com.ochobits.authentication.domain.exceptions.AuthenticationFailure;
import com.ochobits.authentication.domain.exceptions.UserNotFount;
import com.ochobits.authentication.domain.model.User;
import com.ochobits.authentication.domain.ports.PasswordEncoderPort;
import com.ochobits.authentication.domain.ports.TokenGenerator;
import com.ochobits.authentication.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Service;

/*DEFINO COMO SERVICIO PARA QUE LO RECONOSCA SPRING Y NO TENES QUE HACER UN BEAN*/
@Service
public class LoginUserUseCase {

    /*SE HACE USO DE LOS PUERTOS PARA PODER TENER ACCESO A LA INFORMACIÓN QUE SE DESEA*/
    private final UserRepositoryPort userRepositoryPort;

    /*SE HASE USO DEL PUERTO CORRESPONDIENTE PARA ÑA ENCRIPTACIÓN Y VALIDAR*/
    private final PasswordEncoderPort passwordEncoderPort;

    /*SE HACE USO DEL PUERTO PARA LA CREACIÓN DEL TOKEN*/
    private final TokenGenerator tokenGenerator;

    public LoginUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGenerator tokenGenerator
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGenerator = tokenGenerator;
    }


    /*AQUI SE REALIZA LA LOGICA DE NEGOCIO*/
    /*
     * 1. Buscar al usuario por email en la bdd, si no existe retornar error
     * 2. Validar si la clave ingresada en igual al que esta almacenada, si no retornar error
     */
    /*COMO PARAMETRO SE UNA UN RECORD PARA SOLO ENVIAR LA INFORMACIÓN REQUERIDA Y ESTA NO SEA ALTERADA EN EL CAMINO*/
//    public User execute(LoginUserRequest request){
//        var userOpt = userRepositoryPort.findUserByEmail(request.email());
//        if (userOpt.isEmpty()){
//            throw new UserNotFount();
//        }
//
//        var user = userOpt.get();
//        if(!user.getPassword().equals(request.password())){
//            throw new AuthenticationFailure();
//        }
//
//        return user;
//    }

    /*AQUI SE REALIZA LA LOGICA DE NEGOCIO*/
    /*
     * 1. Buscar al usuario por email en la bdd, si no existe retornar error
     * 2. Validar si la clave ingresada en igual al que esta almacenada, si no retornar error
     */
//    public User execute(LoginUserRequest request){
//        var userOpt = userRepositoryPort.findUserByEmail(request.email());
//        if (userOpt.isEmpty()){
//            throw new UserNotFount();
//        }
//
//        var user = userOpt.get();
//        if(!passwordEncoderPort.match(request.password(), user.getPassword())){
//            throw new AuthenticationFailure();
//        }
//
//        return user;
//    }

    /*AQUI SE REALIZA LA LOGICA DE NEGOCIO*/
    /*
     * 1. Buscar al usuario por email en la bdd, si no existe retornar error
     * 2. Validar si la clave ingresada en igual al que esta almacenada, si no retornar error
     * 3. Se genera un Token JWT
     * 4. Se regresa el token
     */
    public LoginUserResponse execute(LoginUserRequest request){
        var userOpt = userRepositoryPort.findUserByEmail(request.email());
        if (userOpt.isEmpty()){
            throw new UserNotFount();
        }

        var user = userOpt.get();
        if(!passwordEncoderPort.match(request.password(), user.getPassword())){
            throw new AuthenticationFailure();
        }

        var token = tokenGenerator.generate(user);
        return new LoginUserResponse(token);
    }

}
