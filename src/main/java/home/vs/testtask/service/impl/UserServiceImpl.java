package home.vs.testtask.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.vs.testtask.model.User;
import home.vs.testtask.repository.UserRepository;
import home.vs.testtask.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    
    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getByLogin(String login) {
        return this.repository.findByLogin(login).orElse(null);
    }

    @Override
    public User getById(Long id) {
        return this.repository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return this.repository.findAll();
    }

    @Override
    public User create(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public User deleteById(Long id) {
        Optional<User> user = this.repository.findById(id);
        if (!user.isEmpty()) {
            this.repository.deleteById(id);
            return user.get();
        }
        return null;
    }
}
