package edu.esoft.com.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDTO {
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String telNo;
}
