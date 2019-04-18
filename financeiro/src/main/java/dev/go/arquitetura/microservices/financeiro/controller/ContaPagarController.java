package dev.go.arquitetura.microservices.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.go.arquitetura.microservices.financeiro.service.ContaPagarService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/admin/contas/pagar")
@Api(value = "Endpoints para gerenciar contas a pagar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContaPagarController {
	
	private final ContaPagarService contaPagarService;

	@GetMapping
	public ResponseEntity<?> receber() {

		log.info("Conta cobrada");

		contaPagarService.realizarCobranca();
		return ResponseEntity
					.ok()
					.build();
	}
}