package com.example.digital_evm.Entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "voter_entries")
public class Voter { //POJO
    @Id
    private ObjectId id;

    private String name;

    @Min(value = 18, message = "Age should be greater than or equal to 18!!")
    @Max(value = 60, message = "Age must be less than or equal to 60!!")
    private int age;

    private String gender;


    private String partyName;

    @NotBlank(message = "adharNumber is considered as your unique identification key so it should not be null!!")
    @Pattern(regexp = "^[0-9]{12}$", message = "adharNumber should contains only numeric value and having length 12!!")
    @Indexed(unique = true)
    private String adharNumber;

    private String password;
    private List<String> Roles = new ArrayList<>(10000);

}
