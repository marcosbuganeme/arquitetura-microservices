package dev.go.arquitetura.microservices.financeiro.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContaPagarService {

	@Transactional
	public void realizarCobranca() {

		log.info("cobranca realizada");
	}
}