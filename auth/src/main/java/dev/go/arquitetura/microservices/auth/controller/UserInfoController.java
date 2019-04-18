package dev.go.arquitetura.microservices.auth.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
@Api(value = "Endpoints para gerenciar informações do usuário")
public class UserInfoController {

    @GetMapping("info")
    @ApiOperation(value = "Recuperará as informações do usuário disponíveis no token", response = ApplicationUser.class)
    public ResponseEntity<ApplicationUser> informacoesUsuario(Principal principal) {

        ApplicationUser user = (ApplicationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ResponseEntity.ok(user);
    }
}