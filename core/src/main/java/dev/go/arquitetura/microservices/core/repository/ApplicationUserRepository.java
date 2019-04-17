package dev.go.arquitetura.microservices.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

	ApplicationUser findByUsername(String username);
}