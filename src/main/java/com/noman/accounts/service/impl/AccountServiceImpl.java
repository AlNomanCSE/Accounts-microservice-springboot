package com.noman.accounts.service.impl;

import com.noman.accounts.constants.AccountsConstants;
import com.noman.accounts.dto.AccountsDTO;
import com.noman.accounts.dto.CustomerDTO;
import com.noman.accounts.entity.Accounts;
import com.noman.accounts.entity.Customer;
import com.noman.accounts.exception.CustomerAlreadyExistsException;
import com.noman.accounts.exception.ResourceNotFoundException;
import com.noman.accounts.mapper.AccountsMapper;
import com.noman.accounts.mapper.CustomerMapper;
import com.noman.accounts.repository.AccountsRepository;
import com.noman.accounts.repository.CustomerRepository;
import com.noman.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        if (customerRepository.findByMobileNumber(customer.getMobileNumber()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with the mobile number: " + customer.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy(customer.getName());
        Customer saveCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(saveCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(String.valueOf(100000L + new Random().nextInt(90000000)));
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy(customer.getName());
        return newAccount;
    }


    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "account ID", customer.getCustomerId().toString()));
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(account, new AccountsDTO()));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        // Find customer by mobile number
        Customer customer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerDTO.getMobileNumber()));

        // Map updated customer details
        CustomerMapper.mapToCustomer(customerDTO, customer);
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setUpdatedBy(customer.getName()); // You might want to get this from a security context in a real app
        customerRepository.save(customer);

        // Update account details if provided
        if (customerDTO.getAccountsDTO() != null) {
            Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
            AccountsMapper.mapToAccounts(customerDTO.getAccountsDTO(), account);
            account.setUpdatedAt(LocalDateTime.now());
            account.setUpdatedBy(customer.getName());
            accountsRepository.save(account);
            isUpdated = true;
        }

        return isUpdated || !customerDTO.getAccountsDTO().equals(fetchAccount(customerDTO.getMobileNumber()).getAccountsDTO());
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        //    firsts delete account details
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        //    second delete customer details
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
