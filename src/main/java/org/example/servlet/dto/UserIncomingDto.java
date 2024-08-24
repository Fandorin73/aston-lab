package org.example.servlet.dto;


public class UserIncomingDto {
    private String firstName;
    private String lastName;


    public UserIncomingDto() {
    }

    public UserIncomingDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}

