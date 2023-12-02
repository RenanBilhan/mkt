package com.example.mkt.service;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.usuario.UsuarioLogadoDTO;
import com.example.mkt.dto.usuario.UsuarioOutputDTO;
import com.example.mkt.entity.CargoEntity;
import com.example.mkt.entity.UsuarioEntity;
import com.example.mkt.entity.enums.Cargo;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.repository.CargoRepository;
import com.example.mkt.repository.UsuarioRepository;
import com.example.mkt.util.ConversorMapper;
import com.example.mkt.util.GetContextUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CargoRepository cargoRepository;
    private final GetContextUser getContextUser;

    public Optional<UsuarioEntity> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public List<UsuarioOutputDTO> findAll(){
        return usuarioRepository.findAll().stream()
                .map(UsuarioOutputDTO::new)
                .collect(Collectors.toList());

    }

    public Integer getIdLogedUser() throws RegraDeNegocioException {
        try{
            return getContextUser.idLogedUser();
        }catch (Exception e){
            throw new RegraDeNegocioException("Nenhum usuario logado");
        }
    }

    public UsuarioLogadoDTO getLogedUser() throws RegraDeNegocioException {

        UsuarioLogadoDTO usuarioLogadoDTO = new UsuarioLogadoDTO();

        try {
            BeanUtils.copyProperties(getById(getContextUser.idLogedUser()), usuarioLogadoDTO);
            return usuarioLogadoDTO;
        }catch (Exception e){
            throw new RegraDeNegocioException("Nenhum usuario logado.");
        }
    }

    public UsuarioOutputDTO getById(Integer idUsuario){

        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(idUsuario);

        if(optionalUsuarioEntity.isEmpty()){
            throw new EntitiesNotFoundException("Usuario não encontrado.");
        }

        return ConversorMapper.converterUsuarioParaDTO(optionalUsuarioEntity.get());
    }
    public UsuarioOutputDTO cadastrarAdmin(LoginInputDTO loginInputDTO, Cargo cargo){

        UsuarioEntity novoUsuario = new UsuarioEntity();
        String senhaCript = passwordEncoder.encode(loginInputDTO.getSenha());
        Optional<CargoEntity> optionalCargoEntity = cargoRepository.findByNome(cargo.toString());

        if(optionalCargoEntity.isEmpty()){
            throw new EntitiesNotFoundException("Cargo não encontrado.");
        }

        novoUsuario.setLogin(loginInputDTO.getLogin());
        novoUsuario.setSenha(senhaCript);
        novoUsuario.getCargos().add(optionalCargoEntity.get());

        UsuarioEntity usuarioSalvo = usuarioRepository.save(novoUsuario);
        UsuarioOutputDTO retorno = ConversorMapper.converter(novoUsuario, UsuarioOutputDTO.class);

        retorno.getCargo().add(usuarioSalvo.getCargos().stream()
                .findFirst()
                .orElseThrow(() -> new EntitiesNotFoundException("Cargo Não encontrado")));

        return retorno;
    }

//    public UsuarioLogadoDTO getLoggedUser() throws RegraDeNegocioException{
//        try{
//            UsuarioLogadoDTO usuarioLogadoDTO;
//            usuarioLogadoDTO = ConversorMapper.converter(findById(getIdLoggedUser()), UsuarioLogadoDTO.class);
//            return usuarioLogadoDTO;
//        }catch (NumberFormatException e){
//            throw new RegraDeNegocioException("Nenhum usuário logado.")
//        }
//    }

}
