package com.example.mkt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoggedinUserDTO {

    private Integer idUser;

    private String login;

    private boolean enabled;
}