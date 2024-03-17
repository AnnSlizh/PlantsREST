package by.slizh.plants.repository.impl;

import by.slizh.plants.domain.exception.ResourceMappingException;
import by.slizh.plants.domain.user.Role;
import by.slizh.plants.domain.user.User;
import by.slizh.plants.repository.DataSourceConfig;
import by.slizh.plants.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT id, username, name, password
            FROM public.user
            WHERE id = ?
            """;

    private final String FIND_BY_USERNAME = """
            SELECT id, username, name, password
            FROM public.user
            WHERE username = ?
            """;

    private final String FIND_ROLES_BY_USER_ID = """
            SELECT r.name AS role
            FROM user_role u_r
            JOIN role r
            ON u_r.role_id = r.id
            WHERE u_r.user_id = ?
            ORDER BY r.id
            """;

    private final String FIND_ROLES_BY_USERNAME = """
            SELECT r.name AS role
            FROM user_role u_r
            JOIN role r
            ON u_r.role_id = r.id
            JOIN public.user u
            ON u_r.user_id = u.id
            WHERE u.username = ?
            ORDER BY r.id
            """;

    private final String CREATE_USER = """
            INSERT INTO public.user (username, name, password)
            VALUES (?, ?, ?)
            """;

    private final String INSERT_USER_ROLE = """
            INSERT INTO user_role
            VALUES (?, ?)
            """;

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet userRs = statement.executeQuery()) {
                statement = connection.prepareStatement(FIND_ROLES_BY_USER_ID);
                statement.setLong(1, id);
                try (ResultSet rolesRs = statement.executeQuery()) {
                    if (userRs.next()) {
                        User user = new User();
                        Set<Role> roles = new TreeSet<>(Comparator.comparingInt(Role::ordinal));
                        user.setId(userRs.getLong("id"));
                        user.setUsername(userRs.getString("username"));
                        user.setName(userRs.getString("name"));
                        user.setPassword(userRs.getString("password"));
                        while (rolesRs.next()) {
                            roles.add(Role.valueOf(rolesRs.getString("role")));
                        }
                        user.setRoles(roles);
                        return Optional.of(user);
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while finding user by id", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME);
            statement.setString(1, username);
            try (ResultSet userRs = statement.executeQuery()) {
                statement = connection.prepareStatement(FIND_ROLES_BY_USERNAME);
                statement.setString(1, username);
                try (ResultSet rolesRs = statement.executeQuery()) {
                    if (userRs.next()) {
                        User user = new User();
                        Set<Role> roles = new TreeSet<>(Comparator.comparingInt(Role::ordinal));
                        user.setId(userRs.getLong("id"));
                        user.setUsername(userRs.getString("username"));
                        user.setName(userRs.getString("name"));
                        user.setPassword(userRs.getString("password"));
                        while (rolesRs.next()) {
                            roles.add(Role.valueOf(rolesRs.getString("role")));
                        }
                        user.setRoles(roles);
                        return Optional.of(user);
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while finding user by username", e);
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            statement = connection.prepareStatement(FIND_BY_USERNAME);
            statement.setString(1, user.getUsername());     // Находим нового юзера по юзернейму,
            try (ResultSet rs = statement.executeQuery()) {            // чтобы достать айдишник
                rs.next();
                user.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating user", e);
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setInt(2, role.ordinal() + 1); // Номер позиции в Enum соответствует id роли в базе
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while inserting role", e);
        }
    }
}
