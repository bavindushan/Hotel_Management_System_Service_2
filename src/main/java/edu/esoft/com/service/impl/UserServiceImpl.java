package edu.esoft.com.service.impl;

import edu.esoft.com.dto.UserDTO;
import edu.esoft.com.entity.User;
import edu.esoft.com.repository.UserRepository;
import edu.esoft.com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final ModelMapper mapper;

    @Override
    public UserDTO createUser(UserDTO dto) {
        User user = mapper.map(dto, User.class);
        User saved = repo.save(user);
        return mapper.map(saved, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO dto) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update relevant fields
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPasswordHash());

        // Optionally update nested Role and Branch
        user.setRole(mapper.map(dto.getRole(), user.getRole().getClass()));
        user.setBranch(mapper.map(dto.getBranch(), user.getBranch().getClass()));

        User updated = repo.save(user);
        return mapper.map(updated, UserDTO.class);
    }

    @Override
    public void deleteUser(Integer id) {
        repo.deleteById(id);
    }
}

