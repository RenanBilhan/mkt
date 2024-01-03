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

    private final AddressRepository enderecoRepository;
    private final ClientRepository clienteRepository;
    private final ClientService clienteService;

    public List<AddressOutputDTO> findAll(){
        return enderecoRepository.findAll().stream()
                .map(AddressOutputDTO::new)
                .collect(Collectors.toList());
    }

    public AddressOutputDTO save(AddressInputDTO enderecoInputDTO, Integer idCliente){

        Optional<ClientEntity> clienteOptional = clienteRepository.findById(idCliente);

        if(clienteOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        ClientEntity cliente = clienteOptional.get();

        AddressEntity enderecoEntity = new AddressEntity();
        BeanUtils.copyProperties(enderecoInputDTO, enderecoEntity);
        enderecoEntity.getClientes().add(cliente);

        AddressEntity enderecoSalvo = enderecoRepository.save(enderecoEntity);
        AddressOutputDTO retorno = ConversorMapper.converter(enderecoSalvo, AddressOutputDTO.class);

        return retorno;
    }

    public AddressOutputDTO findById(Integer idEndereco){
        Optional<AddressEntity> enderecoEntityOptional = enderecoRepository.findById(idEndereco);

        if (enderecoEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Endereço não encontrado.");
        }

        return ConversorMapper.converter(enderecoEntityOptional.get(), AddressOutputDTO.class);
    }

    public List<AddressOutputDTO> findByIdCliente(Integer idCliente){
        List<AddressEntity> enderecoEntityList = enderecoRepository.findAll();
        List<AddressEntity> enderecoRetorno = new ArrayList<>();

        if(clienteRepository.findById(idCliente).isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        for (AddressEntity enderecoEntity: enderecoEntityList) {
            Set<ClientEntity> clienteList = enderecoEntity.getClientes();
            for (ClientEntity cliente: clienteList){
                if(cliente.getIdCliente() == idCliente){
                    enderecoRetorno.add(enderecoEntity);
                }
            }
        }

        return enderecoRetorno.stream()
                .map(AddressOutputDTO::new)
                .collect(Collectors.toList());
    }
}
