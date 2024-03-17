package by.slizh.plants.web.contoller.plant;

import by.slizh.plants.domain.plant.Plant;
import by.slizh.plants.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @GetMapping
    public List<Plant> getAll() {
        return plantService.getAll();
    }

    @GetMapping("/{id}")
    public Plant getById(@PathVariable Integer id) {
        return plantService.getById(id);
    }
}
