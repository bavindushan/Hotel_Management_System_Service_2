package edu.esoft.com.service.impl;

import edu.esoft.com.dto.CustomerDTO;
import edu.esoft.com.entity.Customer;
import edu.esoft.com.repository.CustomerRepository;
import edu.esoft.com.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        if (repo.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        Customer entity = mapper.map(dto, Customer.class);

        // if you ever pass a plain‑text pwd via DTO you could:
        // entity.setPasswordHash(encoder.encode(dto.getPassword()));
        // (DTO currently has no password field, so skip)

        return mapper.map(repo.save(entity), CustomerDTO.class);
    }

    @Override
    public CustomerDTO getById(Integer id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return mapper.map(c, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(c -> mapper.map(c, CustomerDTO.class))
                .toList();
    }

    @Override
    public CustomerDTO update(Integer id, CustomerDTO dto) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        c.setFullName(dto.getFullName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        c.setAddress(dto.getAddress());

        return mapper.map(repo.save(c), CustomerDTO.class);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
