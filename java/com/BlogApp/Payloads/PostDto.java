package com.BlogApp.Payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer id;
	
	@NotBlank
	@Size(min = 5, max = 100, message = "Title must be between 5 and 100")
	private String title;

	@NotBlank
	@Size(min = 5, max = 100, message = "Content must be between 5 and 100")
	private String content;
	
    private String imageName;
	
	private Date addDate;
	
	private CategoryDto category;

	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
	
}
