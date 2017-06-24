package fr.nantes.uste.demowebservice.web.service;

import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.converter.UserConverter;
import fr.nantes.uste.demowebservice.web.entity.UserEntity;
import fr.nantes.uste.demowebservice.web.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ughostephan on 23/06/2017.
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository repository;
    @Autowired
    private UserConverter converter;

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<User> getAll() {
        List<User> beans = new ArrayList<>();
        repository.findAll().forEach(e -> beans.add(converter.toBean(e)));
        return beans;
    }

    /**
     * Gets by id.
     *
     * @param uid the uid
     * @return the by id
     */
    public User getById(String uid) {
        return converter.toBean(repository.findOne(uid));
    }

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     */
    public User getByEmail(String email) {
        return converter.toBean(repository.findByEmail(email));
    }

    /**
     * Add user.
     *
     * @param user the user
     * @return the user
     */
    public User add(User user) {
        UserEntity entityAdded = repository.save(converter.toEntity(user));
        return converter.toBean(entityAdded);
    }
}
