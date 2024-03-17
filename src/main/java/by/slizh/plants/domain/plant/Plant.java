package by.slizh.plants.domain.plant;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Plant {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}
