package com.noman.accounts.service.impl;

import com.noman.accounts.constants.AccountsConstants;
import com.noman.accounts.dto.CustomerDTO;
import com.noman.accounts.entity.Accounts;
import com.noman.accounts.entity.Customer;
import com.noman.accounts.exception.CustomerAlreadyExistsException;
import com.noman.accounts.mapper.CustomerMapper;
import com.noman.accounts.repository.AccountsRepository;
import com.noman.accounts.repository.CustomerRepository;
import com.noman.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDTO
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        if (customerRepository.findByMobileNumber(customer.getMobileNumber()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with the mobile number: " + customer.getMobileNumber());
        }
        Customer saveCustomer = customerRepository.save(customer);
        System.out.println(" Mobile :" + saveCustomer.getCustomerId());
        accountsRepository.save(createNewAccount(saveCustomer));
    }

    /**
     * @param customer
     * @return
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(String.valueOf(100000L + new Random().nextInt(90000000)));
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ARDRESS);
        return newAccount;
    }
}
