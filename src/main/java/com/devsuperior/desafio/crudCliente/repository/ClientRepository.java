package com.devsuperior.desafio.crudCliente.repository;

import com.devsuperior.desafio.crudCliente.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
