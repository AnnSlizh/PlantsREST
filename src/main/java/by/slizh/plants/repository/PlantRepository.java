package by.slizh.plants.repository;

import by.slizh.plants.domain.plant.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantRepository {

    List<Plant> findAll();

    Optional<Plant> findById(Integer id);
}
