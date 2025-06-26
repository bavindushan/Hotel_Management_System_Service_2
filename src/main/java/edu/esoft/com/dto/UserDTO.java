package edu.esoft.com.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String passwordHash;
    private RoleDTO role;
    private BranchDTO branch;
}