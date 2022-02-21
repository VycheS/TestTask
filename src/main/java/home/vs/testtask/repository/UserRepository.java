package home.vs.testtask.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import home.vs.testtask.model.Role;
import home.vs.testtask.model.User;

@Repository
public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = Stream.of(
            new User(1L, "one", "ps", Role.USER, "oneoneone"),
            new User(2L, "two", "ps", Role.USER, "twotwotwo"),
            new User(3L, "three", "ps", Role.USER, "threethreethree")
        ).collect(Collectors.toList());
    }

    public User getByLogin(String login) {
        return this.users.stream()
            .filter(user -> user.getLogin().equals(login))
            .findFirst()
            .orElse(null);
    }

    public User getById(Long id) {
        return this.users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<User> getAll() {
        return this.users;
    }

    public User create(User user) {
        this.users.add(user);
        return user;
    }

    public User deleteById(Long id) {
        User removed = this.users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (removed != null) {
            this.users.removeIf(user -> user.getId().equals(id));
        }
        return removed;
    }
}
