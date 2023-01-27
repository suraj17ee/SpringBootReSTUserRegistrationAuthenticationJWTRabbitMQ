package com.stackroute.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Email(message="invalid email!!")
    private String userEmail;

    @Size(min = 5, max = 10, message = "invalid password!!")
    private String userFirstName;
    private String userLastName;

    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered!!")
    private String userMobileNumber;

    private Date userDob;
    private String userGender;

    @Size(min = 5, max = 10, message = "invalid password!!")
    private String userPassword;

    private List<Address> userAddress;
    private byte[] image;
    private String userRole;

}
