package com.BlogApp.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private Integer id;

	@NotBlank
	@Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
	private String name;

	@Email(message = "Enter a valid email address")
	private String email;

	@NotBlank
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	private String password;

	@NotBlank
	private String about;
}
