package com.example.mkt.service;

import com.example.mkt.dto.cliente.ClienteInputDTO;
import com.example.mkt.dto.cliente.ClienteOutputDTO;
import com.example.mkt.dto.message.StatusMessage;
import com.example.mkt.entity.ClienteEntity;
import com.example.mkt.entity.enums.GeneroPessoa;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.FormatNotValid;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.repository.ClienteRepository;
import com.example.mkt.repository.UsuarioRepository;
import com.example.mkt.util.ConversorImagem;
import com.example.mkt.util.ConversorMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ClienteOutputDTO> findAll(){
        return clienteRepository.findAll().stream()
                .map(ClienteOutputDTO::new)
                .collect(Collectors.toList());
    }

    public ClienteOutputDTO findById(Integer idCliente){

        Optional<ClienteEntity> clienteEntityOptional = clienteRepository.findById(idCliente);

        if(clienteEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado");
        }

        return ConversorMapper.converterClienteParaDTO(clienteEntityOptional.get());
    }

    public String findClienteFoto(Integer idCliente) throws RegraDeNegocioException {

        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(idCliente);

        if(clienteOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        ClienteEntity cliente = clienteOptional.get();

        if(cliente.getFotoCliente() == null){
            throw new RegraDeNegocioException("Cliente não tem foto.");
        }

        byte[] fotoByte = Base64.encodeBase64(cliente.getFotoCliente());
        String fotoString = new String(fotoByte);

        return fotoString;
    }

    public ClienteOutputDTO save(ClienteInputDTO clienteInputDTO, Integer idUsuario, GeneroPessoa generoPessoa){
        ClienteEntity novoCliente = new ClienteEntity();

        novoCliente.setUsuarioEntity(usuarioRepository.findById(idUsuario).get());
        novoCliente.setGenero(generoPessoa.toString());
        novoCliente.setNomeCliente(clienteInputDTO.getNomeCliente());
        novoCliente.setEmailCliente(clienteInputDTO.getEmailCliente());
        novoCliente.setCpf(clienteInputDTO.getCpf());
        novoCliente.setDataNascimento(clienteInputDTO.getDataNascimento());
        novoCliente.setTelefone(clienteInputDTO.getTelefone());

        ClienteEntity clienteSalvo = clienteRepository.save(novoCliente);
        ClienteOutputDTO retorno = new ClienteOutputDTO();
        BeanUtils.copyProperties(clienteSalvo, retorno);
        return retorno;
    }

    public ClienteOutputDTO update(Integer idCliente, ClienteInputDTO clienteInputDTO, GeneroPessoa generoPessoa){
        Optional<ClienteEntity> clienteAtualizarOptional = clienteRepository.findById(idCliente);

        if(clienteAtualizarOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }

        ClienteEntity clienteAtualizar = clienteAtualizarOptional.get();

        String nomeCliente = clienteInputDTO.getNomeCliente();

        String emailCliente = clienteInputDTO.getEmailCliente();

        String cpf = clienteInputDTO.getCpf();

        LocalDate dataNascimento = clienteInputDTO.getDataNascimento();

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

        ClienteEntity clienteAtulizado = clienteRepository.save(clienteAtualizar);
        ClienteOutputDTO retorno = new ClienteOutputDTO();
        BeanUtils.copyProperties(clienteAtulizado, retorno);
        return retorno;
    }

    public StatusMessage updateFotoCliente(MultipartFile fotoPerfil, Integer idCliente) throws FormatNotValid, IOException {
        Optional<ClienteEntity> clienteEntityOptional = clienteRepository.findById(idCliente);

        if(clienteEntityOptional.isEmpty()){
            throw new EntitiesNotFoundException("Cliente não encontrado.");
        }
        if(!ConversorImagem.isImageFile(fotoPerfil)){
            throw new FormatNotValid("Formato de arquivo não suportado.");
        }

        byte[] foto = ConversorImagem.converterParaImagem(fotoPerfil);

        ClienteEntity clienteAtualizado = clienteEntityOptional.get();
        clienteAtualizado.setFotoCliente(foto);

        clienteRepository.save(clienteAtualizado);

        HttpStatus status = HttpStatus.OK;
        StatusMessage statusMessage = new StatusMessage(Instant.now(), status.value(), "Foto atualizada com sucesso.");

        return statusMessage;
    }
}
