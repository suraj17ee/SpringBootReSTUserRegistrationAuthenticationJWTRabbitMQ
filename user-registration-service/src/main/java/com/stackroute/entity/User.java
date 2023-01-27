package com.stackroute.entity;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Users")
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private Integer userRegisterId;

    @Field("user_email")
    private String userEmail;
    @Field("user_firstName")
    private String userFirstName;
    @Field("user_lastName")
    private String userLastName;
    @Field("user_mobileNumber")
    private String userMobileNumber;
    @Field("user_dob") //"yyyy-MM-dd"
    private Date userDob;
    @Field("user_gender")
    private String userGender;
    @Transient
    private String userPassword;
    @Field("user_role")
    private String userRole;

    @Field("user_address")
    private List<Address> userAddress;

    @Field("user_image")
    private byte[] image;

}

