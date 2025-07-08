package com.devsuperior.desafio.crudCliente.services;

import com.devsuperior.desafio.crudCliente.dto.ClientDTO;
import com.devsuperior.desafio.crudCliente.entites.Client;
import com.devsuperior.desafio.crudCliente.repository.ClientRepository;
import com.devsuperior.desafio.crudCliente.services.exceptions.DatabaseException;
import com.devsuperior.desafio.crudCliente.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){

       Client entity = repository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("cliente inexistente")
       );
       return new ClientDTO(entity);

    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> entityAll = repository.findAll(pageable);
        return entityAll.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto){

        Client entity = new Client();
        copyToDto(dto,entity);
        entity = repository.save(entity);

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id,ClientDTO dto){

        try {
            Client entity = repository.getReferenceById(id);
            copyToDto(dto,entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("cliente inexistente");
        }


    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("cliente inexistente");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }



    public void copyToDto(ClientDTO dto,Client entity){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());
    }
}
