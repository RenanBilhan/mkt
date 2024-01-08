package com.example.mkt.service;

import com.example.mkt.dto.address.AddressInputDTO;
import com.example.mkt.dto.address.AddressOutputDTO;
import com.example.mkt.entity.ClientEntity;
import com.example.mkt.entity.AddressEntity;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.repository.ClientRepository;
import com.example.mkt.repository.AddressRepository;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    public List<AddressOutputDTO> findAll(){
        return addressRepository.findAll().stream()
                .map(AddressOutputDTO::new)
                .collect(Collectors.toList());
    }

    public AddressOutputDTO save(AddressInputDTO addressInputDTO, Integer idClient){

        Optional<ClientEntity> clienteOptional = clientRepository.findById(idClient);

        if(clienteOptional.isEmpty()){
            throw new EntitiesNotFoundException("Client not found.");
        }

        ClientEntity client = clienteOptional.get();

        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(addressInputDTO, addressEntity);
        addressEntity.getClients().add(client);

        AddressEntity savedAddress = addressRepository.save(addressEntity);
        AddressOutputDTO retorno = ConversorMapper.converter(savedAddress, AddressOutputDTO.class);

        return retorno;
    }

    public AddressOutputDTO findById(Integer idAddress){
        AddressEntity addressEntity = addressRepository.findById(idAddress).orElseThrow(() -> new EntitiesNotFoundException("Endereço não encontrado."));

        return ConversorMapper.converter(addressEntity, AddressOutputDTO.class);
    }

    public List<AddressOutputDTO> findByIdCliente(Integer idClient){
        List<AddressEntity> addressEntityList = addressRepository.findAll();
        List<AddressEntity> addressReturn = new ArrayList<>();

        if(clientRepository.findById(idClient).isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        for (AddressEntity addressEntity: addressEntityList) {
            Set<ClientEntity> clientList = addressEntity.getClients();
            for (ClientEntity client: clientList){
                if(client.getIdClient() == idClient){
                    addressReturn.add(addressEntity);
                }
            }
        }

        return addressReturn.stream()
                .map(AddressOutputDTO::new)
                .collect(Collectors.toList());
    }
}
