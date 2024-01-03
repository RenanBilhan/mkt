package com.example.mkt.service;

import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.user.LoggedinUserDTO;
import com.example.mkt.dto.user.UserOutputDTO;
import com.example.mkt.entity.RoleEntity;
import com.example.mkt.entity.UserEntity;
import com.example.mkt.entity.enums.Role;
import com.example.mkt.exceptions.EntitiesNotFoundException;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.RoleRepository;
import com.example.mkt.repository.UserRepository;
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
public class UserService {
    private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository cargoRepository;
    private final GetContextUser getContextUser;

    public Optional<UserEntity> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public List<UserOutputDTO> findAll(){
        return usuarioRepository.findAll().stream()
                .map(UserOutputDTO::new)
                .collect(Collectors.toList());

    }

    public Integer getIdLogedUser() throws BussinessRuleException {
        try{
            return getContextUser.idLogedUser();
        }catch (Exception e){
            throw new BussinessRuleException("Nenhum usuario logado");
        }
    }

    public LoggedinUserDTO getLogedUser() throws BussinessRuleException {

        LoggedinUserDTO usuarioLogadoDTO = new LoggedinUserDTO();

        try {
            BeanUtils.copyProperties(getById(getContextUser.idLogedUser()), usuarioLogadoDTO);
            return usuarioLogadoDTO;
        }catch (Exception e){
            throw new BussinessRuleException("Nenhum usuario logado.");
        }
    }

    public UserOutputDTO getById(Integer idUsuario){

        Optional<UserEntity> optionalUsuarioEntity = usuarioRepository.findById(idUsuario);

        if(optionalUsuarioEntity.isEmpty()){
            throw new EntitiesNotFoundException("Usuario não encontrado.");
        }

        return ConversorMapper.converterUsuarioParaDTO(optionalUsuarioEntity.get());
    }

    public UserOutputDTO cadastrarAdmin(LoginInputDTO loginInputDTO, Role cargo){

        UserEntity novoUsuario = new UserEntity();
        String senhaCript = passwordEncoder.encode(loginInputDTO.getSenha());
        Optional<RoleEntity> optionalCargoEntity = cargoRepository.findByNome(cargo.toString());

        if(optionalCargoEntity.isEmpty()){
            throw new EntitiesNotFoundException("Cargo não encontrado.");
        }

        novoUsuario.setLogin(loginInputDTO.getLogin());
        novoUsuario.setSenha(senhaCript);
        novoUsuario.getCargos().add(optionalCargoEntity.get());

        UserEntity usuarioSalvo = usuarioRepository.save(novoUsuario);
        UserOutputDTO retorno = ConversorMapper.converter(novoUsuario, UserOutputDTO.class);

        retorno.getCargo().add(usuarioSalvo.getCargos().stream()
                .findFirst()
                .orElseThrow(() -> new EntitiesNotFoundException("Cargo Não encontrado")));

        return retorno;
    }

}
