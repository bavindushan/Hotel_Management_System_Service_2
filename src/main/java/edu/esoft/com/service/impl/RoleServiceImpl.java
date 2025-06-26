package edu.esoft.com.service.impl;

import edu.esoft.com.dto.RoleDTO;
import edu.esoft.com.entity.Role;
import edu.esoft.com.repository.RoleRepository;
import edu.esoft.com.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        Role saved = roleRepository.save(role);
        RoleDTO result = new RoleDTO();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    @Override
    public RoleDTO getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> {
                    RoleDTO dto = new RoleDTO();
                    BeanUtils.copyProperties(role, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO updateRole(Integer id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setRoleName(roleDTO.getRoleName());
        role.setDescription(roleDTO.getDescription());
        Role updated = roleRepository.save(role);
        RoleDTO result = new RoleDTO();
        BeanUtils.copyProperties(updated, result);
        return result;
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
