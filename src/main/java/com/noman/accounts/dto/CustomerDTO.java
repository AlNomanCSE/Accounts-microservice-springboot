package com.noman.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min=5,max = 30,message = "The length of the customer name should be between 5 to 30")
    private String name;

    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "'Email Address should be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{11})",message = "Mobile number must be 11 digits")
    private String mobileNumber;
    private AccountsDTO accountsDTO;
}
