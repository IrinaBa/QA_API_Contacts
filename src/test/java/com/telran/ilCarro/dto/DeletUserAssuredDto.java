package com.telran.ilCarro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class DeletUserAssuredDto {
    String email;
    String token;


}
