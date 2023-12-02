package com.example.mkt.service;

import com.example.mkt.dto.cliente.ClienteOutputDTO;
import com.example.mkt.dto.endereco.EnderecoInputDTO;
import com.example.mkt.dto.endereco.EnderecoOutputDTO;
import com.example.mkt.entity.ClienteEntity;
import com.example.mkt.entity.EnderecoEntity;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.repository.ClienteRepository;
import com.example.mkt.repository.EnderecoRepository;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public List<EnderecoOutputDTO> findAll(){
        return enderecoRepository.findAll().stream()
                .map(EnderecoOutputDTO::new)
                .collect(Collectors.toList());
    }

    public EnderecoOutputDTO save(EnderecoInputDTO enderecoInputDTO, Integer idCliente){

        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(idCliente);

        if(clienteOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        ClienteEntity cliente = clienteOptional.get();

        EnderecoEntity enderecoEntity = new EnderecoEntity();
        BeanUtils.copyProperties(enderecoInputDTO, enderecoEntity);
        enderecoEntity.getClientes().add(cliente);

        EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoEntity);
        EnderecoOutputDTO retorno = ConversorMapper.converter(enderecoSalvo, EnderecoOutputDTO.class);

        return retorno;
    }

    public EnderecoOutputDTO findById(Integer idEndereco){
        Optional<EnderecoEntity> enderecoEntityOptional = enderecoRepository.findById(idEndereco);

        if (enderecoEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Endereço não encontrado.");
        }

        return ConversorMapper.converter(enderecoEntityOptional.get(), EnderecoOutputDTO.class);
    }

    public List<EnderecoOutputDTO> findByIdCliente(Integer idCliente){
        List<EnderecoEntity> enderecoEntityList = enderecoRepository.findAll();
        List<EnderecoEntity> enderecoRetorno = new ArrayList<>();

        if(clienteRepository.findById(idCliente).isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        for (EnderecoEntity enderecoEntity: enderecoEntityList) {
            Set<ClienteEntity> clienteList = enderecoEntity.getClientes();
            for (ClienteEntity cliente: clienteList){
                if(cliente.getIdCliente() == idCliente){
                    enderecoRetorno.add(enderecoEntity);
                }
            }
        }

        return enderecoRetorno.stream()
                .map(EnderecoOutputDTO::new)
                .collect(Collectors.toList());
    }
}
