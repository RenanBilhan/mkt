package com.example.mkt.service;

import com.example.mkt.dto.client.ClientInputDTO;
import com.example.mkt.dto.client.ClientOutputDTO;
import com.example.mkt.dto.message.StatusMessage;
import com.example.mkt.entity.ClientEntity;
import com.example.mkt.entity.enums.PersonGender;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.FormatNotValid;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.ClientRepository;
import com.example.mkt.repository.UserRepository;
import com.example.mkt.util.ConversorImage;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clienteRepository;
    private final UserRepository usuarioRepository;

    public List<ClientOutputDTO> findAll(){
        return clienteRepository.findAll().stream()
                .map(ClientOutputDTO::new)
                .collect(Collectors.toList());
    }

    public ClientOutputDTO findById(Integer idCliente){

        Optional<ClientEntity> clienteEntityOptional = clienteRepository.findById(idCliente);

        if(clienteEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado");
        }

        return ConversorMapper.converterClienteParaDTO(clienteEntityOptional.get());
    }

    public String findClienteFoto(Integer idCliente) throws BussinessRuleException {

        Optional<ClientEntity> clienteOptional = clienteRepository.findById(idCliente);

        if(clienteOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        ClientEntity cliente = clienteOptional.get();

        if(cliente.getFotoCliente() == null){
            throw new BussinessRuleException("Cliente não tem foto.");
        }

        byte[] fotoByte = Base64.encodeBase64(cliente.getFotoCliente());
        String fotoString = new String(fotoByte);

        return fotoString;
    }

    public ClientOutputDTO save(ClientInputDTO clienteInputDTO, Integer idUsuario, PersonGender generoPessoa){
        ClientEntity novoCliente = new ClientEntity();

        novoCliente.setUsuarioEntity(usuarioRepository.findById(idUsuario).get());
        novoCliente.setGenero(generoPessoa.toString());
        novoCliente.setNomeCliente(clienteInputDTO.getNameClient());
        novoCliente.setEmailCliente(clienteInputDTO.getEmailClient());
        novoCliente.setCpf(clienteInputDTO.getCpf());
        novoCliente.setDataNascimento(clienteInputDTO.getDateOfBirth());
        novoCliente.setTelefone(clienteInputDTO.getTelefone());

        ClientEntity clienteSalvo = clienteRepository.save(novoCliente);
        ClientOutputDTO retorno = new ClientOutputDTO();
        BeanUtils.copyProperties(clienteSalvo, retorno);
        return retorno;
    }

    public ClientOutputDTO update(Integer idCliente, ClientInputDTO clienteInputDTO, PersonGender generoPessoa){
        Optional<ClientEntity> clienteAtualizarOptional = clienteRepository.findById(idCliente);

        if(clienteAtualizarOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        ClientEntity clienteAtualizar = clienteAtualizarOptional.get();

        String nomeCliente = clienteInputDTO.getNameClient();

        String emailCliente = clienteInputDTO.getEmailClient();

        String cpf = clienteInputDTO.getCpf();

        LocalDate dataNascimento = clienteInputDTO.getDateOfBirth();

        String genero = generoPessoa.toString();

        String telefone = clienteInputDTO.getTelefone();

        if(!clienteAtualizar.getNomeCliente().equals(nomeCliente)){
            clienteAtualizar.setNomeCliente(nomeCliente);
        }
        if(!clienteAtualizar.getEmailCliente().equals(emailCliente)){
            clienteAtualizar.setEmailCliente(emailCliente);
        }
        if(!clienteAtualizar.getCpf().equals(cpf)){
            clienteAtualizar.setCpf(cpf);
        }
        if(!clienteAtualizar.getDataNascimento().toString().equals(dataNascimento.toString())){
            clienteAtualizar.setDataNascimento(dataNascimento);
        }
        if(!clienteAtualizar.getGenero().equals(genero)){
            clienteAtualizar.setGenero(genero);
        }
        if(!clienteAtualizar.getTelefone().equals(telefone)){
            clienteAtualizar.setTelefone(telefone);
        }

        ClientEntity clienteAtulizado = clienteRepository.save(clienteAtualizar);
        ClientOutputDTO retorno = new ClientOutputDTO();
        BeanUtils.copyProperties(clienteAtulizado, retorno);
        return retorno;
    }

    public StatusMessage updateFotoCliente(MultipartFile fotoPerfil, Integer idCliente) throws FormatNotValid, IOException {
        Optional<ClientEntity> clienteEntityOptional = clienteRepository.findById(idCliente);

        if(clienteEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }
        if(!ConversorImage.isImageFile(fotoPerfil)){
            throw new FormatNotValid("Formato de arquivo não suportado.");
        }

        byte[] foto = ConversorImage.converterParaImagem(fotoPerfil);

        ClientEntity clienteAtualizado = clienteEntityOptional.get();
        clienteAtualizado.setFotoCliente(foto);

        clienteRepository.save(clienteAtualizado);

        HttpStatus status = HttpStatus.OK;
        StatusMessage statusMessage = new StatusMessage(Instant.now(), status.value(), "Foto atualizada com sucesso.");

        return statusMessage;
    }
}
