package fr.nantes.uste.demowebservice.web.service;

import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.converter.UserConverter;
import fr.nantes.uste.demowebservice.web.entity.UserEntity;
import fr.nantes.uste.demowebservice.web.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by ughostephan on 23/06/2017.
 */
@Service
public class UserService implements UserDetailsService {

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
        return converter.toBeans(repository.findAll());
    }

    /**
     * Find by id user.
     *
     * @param uid the uid
     * @return the user
     */
    public User findById(final String uid) {
        return converter.toBean(repository.findOne(uid));
    }

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    public User findByEmail(final String email) {
        return converter.toBean(repository.findByEmailAndEnabledTrue(email));
    }

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     */
    public User create(final User user) {
        UserEntity entityAdded = repository.save(converter.toEntity(user));
        return converter.toBean(entityAdded);
    }

    /**
     * Create all.
     *
     * @param users the users
     */
    public void createAll(final List<User> users) {
        repository.save(converter.toEntities(users));
    }

    /**
     * Update.
     *
     * @param user the user
     */
    public void update(final User user) {
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
    public void delete(final String uid) {
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
    public boolean emailAlreadyUsed(final String email) {
        return repository.countByEmail(email) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return user;
        }
    }
}
