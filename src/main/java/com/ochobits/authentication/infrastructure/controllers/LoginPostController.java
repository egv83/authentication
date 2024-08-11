package com.ochobits.authentication.infrastructure.controllers;

import com.ochobits.authentication.application.input.LoginUserRequest;
import com.ochobits.authentication.application.usecases.LoginUserUseCase;
import com.ochobits.authentication.infrastructure.adapters.input.LoginPostRequest;
import com.ochobits.authentication.infrastructure.adapters.output.LoginPostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginPostController {
    /* ESTE ES EL CONTROL QUE INTERACTUARA EL EXTERIOR CON EL INTERIOR DE LA APLICAIÓN*/

    /*SE HACE EL LLAMADO DEL CASO DE USO*/
    private final LoginUserUseCase loginUserUseCase;

    public LoginPostController(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    /*por el momento se retorna un objeto de tipo model User, no es el ENTITY*/
    /*SE HACE EL USO DEL RECORD COMO INPUT PARA EVITAR QUE LA INFOMACIÓN SEA ALTERADA EN EL TRANSCURSO*/
//    @PostMapping("/authentication/login")
//    public ResponseEntity<User> login(@RequestBody LoginPostRequest request) {
//
//        /* CONVIERTO EN REQUEST A UN RECORD QUE TIENE  LOS CAMPOS NECESARIO PARA EL LOGIN*/
//        var loginUserRequest = new LoginUserRequest(
//                request.email(),
//                request.password()
//        );
//
//        var user = loginUserCase.execute(loginUserRequest);
//
//        /*SE RETORNA POR EL MOMENTO COMO USER*/
//        return ResponseEntity.ok(user);
//
//    }

    /* OPCION RETORNANDO DATOS DE USUARIO*/
//    @PostMapping("/authentication/login")
//    public ResponseEntity<LoginPostResponse> login(@RequestBody LoginPostRequest request) {
//
//        /* CONVIERTO EN REQUEST A UN RECORD QUE TIENE  LOS CAMPOS NECESARIO PARA EL LOGIN*/
//        var loginUserRequest = new LoginUserRequest(
//                request.email(),
//                request.password()
//        );
//
//        var user = loginUserUseCase.execute(loginUserRequest);
//        var response = new LoginPostResponse(user.getEmail(), user.getStatus());
//
//        /*SE RETORNA POR EL MOMENTO COMO USER*/
//        return ResponseEntity.ok(response);
//
//    }

    @PostMapping("/authentication/login")
    public ResponseEntity<LoginPostResponse> login(@RequestBody LoginPostRequest request) {

        /* CONVIERTO EN REQUEST A UN RECORD QUE TIENE  LOS CAMPOS NECESARIO PARA EL LOGIN*/
        var loginUserRequest = new LoginUserRequest(
                request.email(),
                request.password()
        );

        var tokenResponse = loginUserUseCase.execute(loginUserRequest);/*ENVIA LOS DATOS DE L USUARIO Y RETORNA UN TOKEN*/
        var response = new LoginPostResponse(tokenResponse.token());/*SE ASIGNA AL AOUTPUT ADAPTER EN TOKEN QUE NO SERA MUTADO EN EL TRANSCURSO*/

        /*SE RETORNA POR EL MOMENTO COMO USER*/
        return ResponseEntity.ok(response);

    }

}
