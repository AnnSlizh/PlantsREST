package by.slizh.plants.service;

import by.slizh.plants.domain.plant.Plant;

import java.util.List;

public interface PlantService {

    List<Plant> getAll();

    Plant getById(Integer id);
}
