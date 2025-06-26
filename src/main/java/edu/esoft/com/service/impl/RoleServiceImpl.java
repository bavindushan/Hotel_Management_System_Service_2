package edu.esoft.com.service.impl;

import edu.esoft.com.dto.RoleDTO;
import edu.esoft.com.entity.Role;
import edu.esoft.com.repository.RoleRepository;
import edu.esoft.com.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;
    private final ModelMapper   mapper;

    @Override
    public RoleDTO createRole(RoleDTO dto) {

        if (repo.existsByRoleName(dto.getRoleName())) {
            throw new IllegalArgumentException("Role name already exists");
        }

        Role saved = repo.save(mapper.map(dto, Role.class));
        return mapper.map(saved, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return repo.findAll()
                .stream()
                .map(role -> mapper.map(role, RoleDTO.class))
                .toList();
    }

    @Override
    public RoleDTO getRoleById(Integer id) {
        Role role = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        return mapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(Integer id, RoleDTO dto) {
        Role role = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());

        Role updated = repo.save(role);
        return mapper.map(updated, RoleDTO.class);
    }

    @Override
    public void deleteRole(Integer id) {
        repo.deleteById(id);
    }
}
