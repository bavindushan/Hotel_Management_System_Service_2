package edu.esoft.com.service;

import edu.esoft.com.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO dto);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Integer id);
    UserDTO updateUser(Integer id, UserDTO dto);
    void deleteUser(Integer id);
}
