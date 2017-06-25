package fr.nantes.uste.demowebservice.web.bean;

/**
 * Created by ughostephan on 23/06/2017.
 */
public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMINISTRATOR("ROLE_ADMINISTRATOR");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
