package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String uId;
    private String name;
    private String address;
    private String nic;
    private String phone;
    private String email;
    private String password;
    private boolean activeStatus;
    private String role;
}
