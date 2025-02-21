package com.noman.accounts.service;

import com.noman.accounts.dto.CustomerDTO;

public interface IAccountService {


    /**
     *
     * @param customerDTO
     */
    void createAccount(CustomerDTO customerDTO);

    /**
     *
     * @param mobileNumber
     * @return
     */
    CustomerDTO fetchAccount(String mobileNumber);
}
