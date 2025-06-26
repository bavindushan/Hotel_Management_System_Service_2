package edu.esoft.com.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
    private Integer roleId;
    private String roleName;
    private String description;
}
