package com.example.mkt.service;

import com.example.mkt.dto.client.ClientInputDTO;
import com.example.mkt.dto.client.ClientOutputDTO;
import com.example.mkt.entity.ClientEntity;
import com.example.mkt.entity.UserEntity;
import com.example.mkt.entity.enums.PersonGender;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.repository.ClientRepository;
import com.example.mkt.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    private UserEntity userMock;

    private ClientEntity clientMock;

    private ClientOutputDTO clientOutputDTOMock;

    private ClientInputDTO clientInputDTOMock;

    @BeforeEach

    void setUp(){
        userMock = new UserEntity(
                1,
                "login",
                "password",
                new HashSet<>()
        );

        clientInputDTOMock = new ClientInputDTO(
                "Renan",
                "renan.renan@renan.com",
                "39716606060",
                LocalDate.of(1995, 04,22),
                "99999999999"
        );

        clientMock = new ClientEntity(
                1,
                "Renan",
                "renan.renan@renan.com",
                "39716606060",
                LocalDate.of(1995, 04,22),
                PersonGender.MALE.toString(),
                new HashSet<>(),
                userMock,
                new byte[] {1, 2, 3},
                "99999999999",
                new HashSet<>()

        );

        clientOutputDTOMock = new ClientOutputDTO(clientMock);
    }

    @Test
    void findAll(){

        List<ClientEntity> clientEntityListMock = List.of(clientMock);
        List<ClientOutputDTO> clientOutputDTOListMock = List.of(clientOutputDTOMock);

        when(clientRepository.findAll()).thenReturn(clientEntityListMock);

        List<ClientOutputDTO> result = clientService.findAll();

        Assertions.assertEquals(result.get(0).getIdClient(), clientOutputDTOListMock.get(0).getIdClient());

    }

    @Test
    void findClientById(){
        Integer id = 1;

        when(clientRepository.findById(1)).thenReturn(Optional.of(clientMock));

        ClientOutputDTO result = clientService.findById(id);

        Assertions.assertEquals( result.getIdClient(), clientMock.getIdClient());
    }

    @Test
    void returnclientPhoto() throws BussinessRuleException {
        Integer id = 1;
        when(clientRepository.findById(1)).thenReturn(Optional.of(clientMock));

        byte[] photoByte = Base64.encodeBase64(clientMock.getPhotoClient());

        String stringPhoto = new String(photoByte);

        String result = clientService.findClientPhoto(id);

        Assertions.assertEquals(result, stringPhoto);
    }

    @Test
    void createClient(){

    }
}
