package fr.nantes.uste.demowebservice.web.service;

import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.converter.UserConverter;
import fr.nantes.uste.demowebservice.web.entity.UserEntity;
import fr.nantes.uste.demowebservice.web.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
     * Find all list.
     *
     * @return the list
     */
    public List<User> findAll() {
        List<User> beans = new ArrayList<>();
        repository.findAll().forEach(e -> beans.add(converter.toBean(e)));
        return beans;
    }

    /**
     * Find by id user.
     *
     * @param uid the uid
     * @return the user
     */
    public User findById(String uid) {
        return converter.toBean(repository.findOne(uid));
    }

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    public User findByEmail(String email) {
        return converter.toBean(repository.findByEmail(email));
    }

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     */
    public User create(User user) {
        UserEntity entityAdded = repository.save(converter.toEntity(user));
        return converter.toBean(entityAdded);
    }

    /**
     * Update.
     *
     * @param user the user
     */
    public void update(User user) {
        UserEntity entity= repository.findOne(user.getUid());

        if (entity == null)
            throw new EntityNotFoundException();

        repository.saveAndFlush(converter.toEntity(user));
    }

    /**
     * Delete.
     *
     * @param uid the uid
     */
    public void delete(String uid) {
        UserEntity entityToDelete = repository.findOne(uid);

        if (entityToDelete == null)
            throw new EntityNotFoundException();

        repository.delete(entityToDelete);
    }

    /**
     * Email already used boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean emailAlreadyUsed(String email) {
        return repository.countUserForEmail(email) > 0;
    }
}
