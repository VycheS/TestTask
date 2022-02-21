package home.vs.testtask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.vs.testtask.model.User;
import home.vs.testtask.repository.UserRepository;
import home.vs.testtask.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    
    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public User getByLogin(String login) {
        return this.repository.getByLogin(login);
    }

    public User getById(Long id) {
        return this.repository.getById(id);
    }

    public List<User> getAll() {
        return this.repository.getAll();
    }

    public User create(User user) {
        return repository.create(user);
    }

    public User deleteById(Long id) {
        return this.repository.deleteById(id);
    }
}
