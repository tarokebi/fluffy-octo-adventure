package foa.tcg.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import foa.tcg.backend.model.dto.Users;
import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class UserJsonDataLoaderUtil implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoaderUtil.class);
    private final UserRepository userRepository;
    private final JdbcClient jdbcClient;
    private final ObjectMapper objectMapper;

    public UserJsonDataLoaderUtil(UserRepository userRepository, JdbcClient jdbcClient, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.jdbcClient = jdbcClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")) {
                Users allUsers = objectMapper.readValue(inputStream, Users.class);
                log.info("--- Reading {} users from JSON data and saving to in-memory collection... ---", allUsers.users().size());
                this.saveAll(allUsers.users());
            } catch (IOException e) {
                throw new RuntimeException("Error reading users.json", e);
            }
        } else {
            log.info("--- Not loading users from JSON data because collection contains data. ---");
        }
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM appuser")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<User> users) {
        users.forEach(userRepository::create);
    }

}
