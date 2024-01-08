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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final GetContextUser getContextUser;

    public Optional<UserEntity> findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public List<UserOutputDTO> findAll(){
        return userRepository.findAll().stream()
                .map(UserOutputDTO::new)
                .collect(Collectors.toList());

    }

    public Integer getIdLogedUser() throws BussinessRuleException {
        try{
            return getContextUser.idLogedUser();
        }catch (Exception e){
            throw new BussinessRuleException("No logged user.");
        }
    }

    public LoggedinUserDTO getLogedUser() throws BussinessRuleException {

        LoggedinUserDTO loggedinUserDTO = new LoggedinUserDTO();

        try {
            BeanUtils.copyProperties(getById(getContextUser.idLogedUser()), loggedinUserDTO);
            return loggedinUserDTO;
        }catch (Exception e){
            throw new BussinessRuleException("No logged user.");
        }
    }

    public UserOutputDTO getById(Integer idUser){

        Optional<UserEntity> optionalUsuarioEntity = userRepository.findById(idUser);

        if(optionalUsuarioEntity.isEmpty()){
            throw new EntitiesNotFoundException("User not found.");
        }

        return ConversorMapper.converterUsuarioParaDTO(optionalUsuarioEntity.get());
    }

    public UserOutputDTO registerAdmin(LoginInputDTO loginInputDTO, Role role){

        UserEntity newUser = new UserEntity();
        String passwordCript = passwordEncoder.encode(loginInputDTO.getPassword());
        RoleEntity cargoEntity = roleRepository.findByName(role.toString()).orElseThrow(() -> new EntitiesNotFoundException("Role not found."));

        newUser.setLogin(loginInputDTO.getLogin());
        newUser.setPassword(passwordCript);
        newUser.getCargos().add(cargoEntity);

        UserEntity savedUser = userRepository.save(newUser);
        UserOutputDTO userReturn = ConversorMapper.converter(newUser, UserOutputDTO.class);

        userReturn.getRole().add(savedUser.getCargos().stream()
                .findFirst()
                .orElseThrow(() -> new EntitiesNotFoundException("Role not found.")));

        return userReturn;
    }

}
