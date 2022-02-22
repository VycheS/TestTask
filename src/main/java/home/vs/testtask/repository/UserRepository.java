package home.vs.testtask.repository;

import java.util.Optional;

import home.vs.testtask.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
