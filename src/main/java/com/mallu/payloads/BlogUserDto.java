package com.mallu.payloads;

import java.util.HashSet;
import java.util.Set;

import com.mallu.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserDto {

	
	private Integer id;
	
	@NotEmpty
	@Size(min=4, max= 20,message="Username must be min 4 and max 20")
	private String name;
	
	@Email(message="Email address is not valid..")
	private String email;
	
	@NotEmpty
	@Size(min=4,max= 10,message="Password must be min 4 and max 10 charecters")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
