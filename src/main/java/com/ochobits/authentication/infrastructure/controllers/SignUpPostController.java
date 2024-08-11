package com.ochobits.authentication.infrastructure.controllers;

import com.ochobits.authentication.application.input.SignUpUserRequest;
import com.ochobits.authentication.application.usecases.SignUpUserUseCase;
import com.ochobits.authentication.infrastructure.adapters.input.SignUpPostRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpPostController {

    /* SE LLAMA AL CASO DE USO*/
    private final SignUpUserUseCase signUpUserUseCase;

    /*SE LLAMA UN LOGGER PARA VER LOS ERRORES ENCONSOLA*/
    private final Logger logger = LoggerFactory.getLogger(SignUpPostController.class);

    public SignUpPostController(SignUpUserUseCase signUpUserUseCase) {
        this.signUpUserUseCase = signUpUserUseCase;
    }

    @PostMapping("/authentication/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpPostRequest body){
        logger.info("Starting signUp - nombre={} email={} password={}",
                body.name(),
                body.email(),
                body.password());

        signUpUserUseCase.execute(new SignUpUserRequest(
                body.name(),
                body.email(),
                body.password()
        ));

        return ResponseEntity.ok().build();
    }
}
