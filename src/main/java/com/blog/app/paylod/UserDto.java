package com.blog.app.paylod;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDto {


	private int id;

	@NotEmpty
	@Size(min=4,message="UserName Must Be Min Of 4 Chars!")
	private String name;


	@Email
	private String email;

	@NotEmpty
	@Size(min=8,message="Password Must Be Min Of 8 Chars!")
	private String password;

	@NotEmpty
	@Size(min=10,message="About Must Be Min Of 10 Chars!")
	private String about;
}
