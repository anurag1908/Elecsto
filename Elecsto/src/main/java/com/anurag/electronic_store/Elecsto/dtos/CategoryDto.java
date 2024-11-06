package com.anurag.electronic_store.Elecsto.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private String categoryId;

    @NotBlank
    private String title;

    @NotBlank(message = "Description required!")
    private String description;


    private String coverImage;

}
