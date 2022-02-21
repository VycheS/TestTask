package home.vs.testtask.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import home.vs.testtask.model.User;
import home.vs.testtask.service.UserService;

@RestController
public class UserRestController implements ApiVersion1 {
    private UserService service;
    
    private final static String PATH = "/api/" + VERSION + "/users";

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getAll() {
        return this.service.getAll();
    }

    @GetMapping(path = PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getById(@PathVariable Long id) {
        return this.service.getById(id);
    }

    @PostMapping(path = PATH)
    public @ResponseBody User create(@RequestBody User user) {
        return this.service.create(user);
    }

    @DeleteMapping(path = PATH + "/{id}")
    public @ResponseBody User deleteById(@PathVariable Long id) {
        return this.service.deleteById(id); // TODO реализовать адекватный http ответ в случае если удаляемого пользователя нет
    }
}
