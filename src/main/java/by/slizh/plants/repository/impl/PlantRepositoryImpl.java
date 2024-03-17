package by.slizh.plants.repository.impl;

import by.slizh.plants.domain.exception.ResourceMappingException;
import by.slizh.plants.domain.plant.Plant;
import by.slizh.plants.repository.DataSourceConfig;
import by.slizh.plants.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlantRepositoryImpl implements PlantRepository {

    private final DataSourceConfig dataSourceConfig;

    private static final String FIND_ALL_PLANTS = """
            select id, name, description, image_url
            from plant
            """;

    private static final String FIND_BY_ID = """
            select id, name, description, image_url
            from plant
            where id = ?
            """;


    @Override
    public List<Plant> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANTS);
            try (ResultSet rs = statement.executeQuery()) {
                List<Plant> plants = new ArrayList<>();
                while (rs.next()) {
                    Plant plant = new Plant();
                    plant.setId(rs.getLong("id"));
                    plant.setName(rs.getString("name"));
                    plant.setDescription(rs.getString("description"));
                    plant.setImageUrl(rs.getString("image_url"));
                    plants.add(plant);
                }
                return plants;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResourceMappingException("Exception while find all plants");
        }
    }

    @Override
    public Optional<Plant> findById(Integer id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                Plant plant = null;
                if (rs.next()) {
                    plant = new Plant();
                    plant.setId(rs.getLong("id"));
                    plant.setName(rs.getString("name"));
                    plant.setDescription(rs.getString("description"));
                    plant.setImageUrl(rs.getString("image_url"));
                }
                return Optional.ofNullable(plant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResourceMappingException("Exception while find plant by id");
        }
    }
}
