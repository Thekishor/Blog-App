package com.BlogApp.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
    private Integer id;
    
    @NotBlank
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
	private String categoryTitle;
    
    @NotBlank
    @Size(min = 5, max = 100, message = "Description must be between 5 and 100 characters")
	private String categoryDescription;
}
