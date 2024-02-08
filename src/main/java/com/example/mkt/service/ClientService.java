package com.example.mkt.service;

import com.example.mkt.dto.client.ClientInputDTO;
import com.example.mkt.dto.client.ClientOutputDTO;
import com.example.mkt.dto.login.LoginInputDTO;
import com.example.mkt.dto.message.StatusMessage;
import com.example.mkt.dto.user.UserOutputDTO;
import com.example.mkt.entity.ClientEntity;
import com.example.mkt.entity.UserEntity;
import com.example.mkt.entity.enums.PersonGender;
import com.example.mkt.entity.enums.Role;
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

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<ClientOutputDTO> findAll(){
        return clientRepository.findAll().stream()
                .map(ClientOutputDTO::new)
                .collect(Collectors.toList());
    }

    public ClientOutputDTO findById(Integer idClient){

        ClientEntity clientEntity = clientRepository.findById(idClient).orElseThrow(() -> new EntitiesNotFoundException("Client not found."));

        return ConversorMapper.converterClienteParaDTO(clientEntity);
    }

    public String findClientPhoto(Integer idClient) throws BussinessRuleException {

        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new EntitiesNotFoundException("Client not found."));

        if(client.getPhotoClient() == null){
            throw new EntitiesNotFoundException("Client has no photo.");
        }

        byte[] photoByte = Base64.encodeBase64(client.getPhotoClient());
        String photoString = new String(photoByte);

        return photoString;
    }

    public ClientOutputDTO save(ClientInputDTO clientInputDTO, LoginInputDTO loginInputDTO, PersonGender personGender){
        ClientEntity newClient = new ClientEntity();

        UserOutputDTO savedUser = userService.registerAdmin(loginInputDTO, Role.ROLE_USER);

        newClient.setUserEntity(userRepository.findById(savedUser.getIdUser()).get());
        newClient.setGender(personGender.toString());
        newClient.setNameClient(clientInputDTO.getNameClient());
        newClient.setEmailClient(clientInputDTO.getEmailClient());
        newClient.setCpf(clientInputDTO.getCpf());
        newClient.setDateOfBirth(clientInputDTO.getDateOfBirth());
        newClient.setPhoneNumber(clientInputDTO.getPhoneNumber());

        ClientEntity savedClient = clientRepository.save(newClient);
        ClientOutputDTO clientReturn = new ClientOutputDTO();
        BeanUtils.copyProperties(savedClient, clientReturn);
        return clientReturn;
    }

    public ClientOutputDTO update(Integer idClient, ClientInputDTO clientInputDTO, PersonGender personGender){
        ClientEntity clientToUpdate = clientRepository.findById(idClient).orElseThrow(() -> new EntitiesNotFoundException("Client not found."));

        String nameClient = clientInputDTO.getNameClient();

        String emailClient = clientInputDTO.getEmailClient();

        String cpf = clientInputDTO.getCpf();

        LocalDate dateOfBirth = clientInputDTO.getDateOfBirth();

        String personGenderString = personGender.toString();

        String phoneNumber = clientInputDTO.getPhoneNumber();

        if(!clientToUpdate.getNameClient().equals(nameClient)){
            clientToUpdate.setNameClient(nameClient);
        }
        if(!clientToUpdate.getEmailClient().equals(emailClient)){
            clientToUpdate.setEmailClient(emailClient);
        }
        if(!clientToUpdate.getCpf().equals(cpf)){
            clientToUpdate.setCpf(cpf);
        }
        if(!clientToUpdate.getDateOfBirth().toString().equals(dateOfBirth.toString())){
            clientToUpdate.setDateOfBirth(dateOfBirth);
        }
        if(!clientToUpdate.getGender().equals(personGenderString)){
            clientToUpdate.setGender(personGenderString);
        }
        if(!clientToUpdate.getPhoneNumber().equals(phoneNumber)){
            clientToUpdate.setPhoneNumber(phoneNumber);
        }

        ClientEntity updatedClient = clientRepository.save(clientToUpdate);
        ClientOutputDTO clientReturn = new ClientOutputDTO();
        BeanUtils.copyProperties(updatedClient, clientReturn);
        return clientReturn;
    }

    public StatusMessage updatePhotoClient(MultipartFile photoProfile, Integer idClient) throws FormatNotValid, IOException {
        ClientEntity clientEntity = clientRepository.findById(idClient).orElseThrow(() -> new EntitiesNotFoundException("Client not found."));

        if(!ConversorImage.isImageFile(photoProfile)){
            throw new FormatNotValid("File format not suported.");
        }

        byte[] photo = ConversorImage.converterParaImagem(photoProfile);

        clientEntity.setPhotoClient(photo);

        ClientEntity updatedClient = clientRepository.save(clientEntity);

        HttpStatus status = HttpStatus.OK;
        StatusMessage statusMessage = new StatusMessage(Instant.now(), status.value(), "Photo updated successful.");

        return statusMessage;
    }
}
