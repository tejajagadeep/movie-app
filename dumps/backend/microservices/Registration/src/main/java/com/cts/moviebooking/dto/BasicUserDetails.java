package com.cts.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicUserDetails {

    private String firstName;
    private String userName;
    private String email;
    private String role;

}
