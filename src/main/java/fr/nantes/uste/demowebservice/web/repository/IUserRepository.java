package fr.nantes.uste.demowebservice.web.repository;

import fr.nantes.uste.demowebservice.web.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ughostephan on 23/06/2017.
 */
@Repository
public interface IUserRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByEmail(String email);
}
