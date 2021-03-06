package fr.nantes.uste.demowebservice.web.converter;

import fr.nantes.uste.demowebservice.web.bean.User;
import fr.nantes.uste.demowebservice.web.entity.UserEntity;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by ughostephan on 23/06/2017.
 */
@Component
public class UserConverter implements IConverter<User, UserEntity> {

    @Override
    public UserEntity toEntity(final User bean) {
        UserEntity u = null;

        if (bean != null) {
            u = new UserEntity();
            u.setUid(bean.getUid());
            u.setFirstname(bean.getFirstname());
            u.setLastname(bean.getLastname());
            u.setEmail(bean.getEmail());
            u.setCountry(bean.getCountry());
            u.setCity(bean.getCity());
            u.setBirthday(bean.getBirthday());
            u.setPassword(bean.getPassword());
            u.setCreated_at(bean.getCreated_at());
            u.setRoles(StringUtils.join(bean.getRoles(), ';'));
            u.setEnabled(bean.isEnabled());
        }

        return u;
    }

    @Override
    public User toBean(final UserEntity entity) {
        User u = null;

        if (entity != null) {
            u = new User();
            u.setUid(entity.getUid());
            u.setFirstname(entity.getFirstname());
            u.setLastname(entity.getLastname());
            u.setEmail(entity.getEmail());
            u.setCountry(entity.getCountry());
            u.setCity(entity.getCity());
            u.setBirthday(entity.getBirthday());
            u.setPassword(entity.getPassword());
            u.setCreated_at(entity.getCreated_at());
            u.setRoles(Arrays.asList(entity.getRoles().split(";")));
            u.setEnabled(entity.isEnabled());
        }

        return u;
    }
}
