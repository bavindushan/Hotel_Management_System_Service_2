package edu.esoft.com.service;

import edu.esoft.com.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO create(CustomerDTO dto);

    CustomerDTO getById(Integer id);

    List<CustomerDTO> getAll();

    CustomerDTO update(Integer id, CustomerDTO dto);

    void delete(Integer id);
}
