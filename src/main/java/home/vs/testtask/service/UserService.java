package home.vs.testtask.service;

import java.util.List;

import home.vs.testtask.model.User;

public interface UserService {
    
    public User getByLogin(String login);

    public User getById(Long id);

    public List<User> getAll();

    public User create(User user);

    public User deleteById(Long id);
}
