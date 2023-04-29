package com.Facturacion.FacturacionII.service;

import com.Facturacion.FacturacionII.exception.AlreadyExistsException;
import com.Facturacion.FacturacionII.exception.NotFoundException;
import com.Facturacion.FacturacionII.model.ClientModel;
import com.Facturacion.FacturacionII.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientModel> create(List<ClientModel> newClients)  throws AlreadyExistsException {
        List<ClientModel> savedClients = new ArrayList<>();
        List<String> existingClients = new ArrayList<>();

        for (ClientModel newClient : newClients) {

            String name = newClient.getName();
            String lastName = newClient.getLastname();
            String docNumber = newClient.getDocnumber();

            if (name == null || name.trim().isEmpty()) {
                throw new AlreadyExistsException("El nombre del cliente no puede estar vacío");
            }

            if (lastName == null || lastName.trim().isEmpty()) {
                throw new AlreadyExistsException("El apellido del cliente no puede estar vacío");
            }

            if (docNumber == null || !docNumber.matches("\\d{6,20}")) {
                throw new AlreadyExistsException("El número de documento '"+ newClient.getDocnumber().toString()+"' del cliente no es válido, debe tener entre 6 y 20 dígitos y no debe contener letras.");
            }

            Optional<ClientModel> clientOp = this.clientRepository.findByDocnumber(newClient.getDocnumber());

            if (clientOp.isPresent()){
                existingClients.add(docNumber);
            } else {
                savedClients.add(newClient);
            }
        }


        if(!existingClients.isEmpty()) {
            String existingClientsMsg = String.join(",", existingClients);
            log.info("Los siguientes clientes ya existen en la base de datos : " + existingClientsMsg);
            throw new AlreadyExistsException("Los siguientes códigos ya existen en la base de datos : " + existingClientsMsg);
        } else {
            return this.clientRepository.saveAll(savedClients);
        }

    }

    public ClientModel update(ClientModel newClient, Integer id) throws Exception {
        log.info("ID INGRESADO : " + id);
        if (id <= 0){
            throw new Exception("El id brindado no es valido");
        }

        String name = newClient.getName();
        String lastname = newClient.getLastname();
        String docNumber = newClient.getDocnumber();

        if (name == null || name.isEmpty()) {
            throw new NotFoundException("El nombre del cliente no puede ser nulo o vacío");
        }

        if (lastname == null || lastname.isEmpty()) {
            throw new NotFoundException("El apellido del cliente no puede ser nulo o vacío");
        }

        if (docNumber == null || !docNumber.matches("\\d{6,20}")) {
            throw new NotFoundException("El número de documento del cliente no es válido, debe tener entre 6 y 20 dígitos y no debe contener letras.");
        }

        Optional<ClientModel> clientOp = this.clientRepository.findById(id);

        if (clientOp.isEmpty()){
            log.info("El cliente que intenta modificar no existe en la base de datos : " + newClient);
            throw new NotFoundException("El cliente que intenta modificar no existe en la base de datos");
        }else {
            log.info("el cliente fue encontrado");
            ClientModel clientBd = clientOp.get();

            clientBd.setName(newClient.getName());
            clientBd.setLastname(newClient.getLastname());
            clientBd.setDocnumber(newClient.getDocnumber());

            log.info("cliente actualizado : " + clientBd);

            return this.clientRepository.save(clientBd);
        }
    }


    public ClientModel findById(Integer id) throws Exception {
        if (id == null || id <= 0){
            throw new Exception("El id brindado no es válido.");
        }

        Optional<ClientModel> clientOp = this.clientRepository.findById(id);

        if (clientOp.isEmpty()){
            log.info("El cliente con el id brindado no existe en la base de datos : " + id);
            throw new NotFoundException("El cliente que intenta solicitar no existe");
        }else {
            return clientOp.get();
        }
    }


    public List<ClientModel> findAll(){
        return this.clientRepository.findAll();
    }
}
