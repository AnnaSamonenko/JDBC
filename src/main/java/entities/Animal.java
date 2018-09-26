package entities;

import java.util.Objects;

public class Animal {

    private int id;
    private String alias;
    private boolean hasOwner;

    public Animal() {
    }

    public Animal(int id, String alias, boolean hasOwner) {
        this.id = id;
        this.alias = alias;
        this.hasOwner = hasOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean getHasOwner() {
        return hasOwner;
    }

    public void setHasOwner(boolean hasOwner) {
        this.hasOwner = hasOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getId() == animal.getId() &&
                getHasOwner() == animal.getHasOwner() &&
                getAlias().equals(animal.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAlias(), getHasOwner());
    }
}
