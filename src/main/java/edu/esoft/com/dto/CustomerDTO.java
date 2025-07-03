package edu.esoft.com.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
