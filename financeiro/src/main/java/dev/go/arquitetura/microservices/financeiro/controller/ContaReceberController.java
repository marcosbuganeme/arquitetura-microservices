package dev.go.arquitetura.microservices.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.go.arquitetura.microservices.financeiro.service.ContaReceberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/admin/contas/receber")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoints para gerenciar contas a receber")
public class ContaReceberController {

	private final ContaReceberService contaReceberService;

	@GetMapping
	public ResponseEntity<?> receber() {

		log.info("conta recebida");
		contaReceberService.receberConta();
		return ResponseEntity
					.ok()
					.build();
	}
}