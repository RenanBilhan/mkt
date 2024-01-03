package com.example.mkt.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressInputDTO {

    @NotNull(message = "This field must be filled.")
    @NotEmpty
    @Size(min = 3, max = 255, message = "Must contain atleast 3 characters.")
    private String Address;

    @NotNull(message = "This field must be filled.")
    @Pattern(regexp = "^[0-9]$", message = "This field must contain only numbers.")
    @Size(min = 1, max = 9, message = "Must contain between 1 and 9 digits.")
    private String number;

    @Size(min = 0, max = 255)
    private String complement;

    @NotNull(message = "This field must be filled.")
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^[0-9]{8}$", message = "This field must contain only numbers.")
    private String zipCode;

    @NotNull(message = "This field must be filled.")
    @NotEmpty
    @Size(min = 3, max = 255)
    private String city;

    @NotNull(message = "This field must be filled.")
    @NotEmpty
    @Size(min = 3, max = 255)
    private String state;

    @NotNull(message = "This field must be filled.")
    @NotEmpty
    @Size(min = 2, max = 2)
    private String federalUnity;
}
