package fr.nantes.uste.demowebservice.web.converter;

/**
 * Created by ughostephan on 23/06/2017.
 *
 * @param <Bean>   the type parameter
 * @param <Entity> the type parameter
 */
public interface IConverter<Bean, Entity> {

    /**
     * Bean To entity.
     *
     * @param bean the bean
     * @return the entity
     */
    Entity toEntity(Bean bean);

    /**
     * Entity To bean.
     *
     * @param entity the entity
     * @return the bean
     */
    Bean toBean(Entity entity);

}
