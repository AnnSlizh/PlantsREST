package by.slizh.plants.service.impl;

import by.slizh.plants.domain.exception.ResourceNotFoundException;
import by.slizh.plants.domain.plant.Plant;
import by.slizh.plants.repository.PlantRepository;
import by.slizh.plants.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Plant> getAll() {
        return plantRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Plant getById(Integer id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant <id:" + id + "> not found"));
    }
}
