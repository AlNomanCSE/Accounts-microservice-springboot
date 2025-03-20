package com.noman.accounts.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AccountsDTO {

    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
