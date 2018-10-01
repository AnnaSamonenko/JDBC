package entities;

import java.util.Objects;

public class Animal {

    private int id;
    private String alias;
    private Human human;

    public Animal() {
    }

    public Animal(int id, String alias, Human human) {
        this.id = id;
        this.alias = alias;
        this.human = human;
    }

    public Animal(int id, String alias) {
        this.id = id;
        this.alias = alias;
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

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getId() == animal.getId() &&
                Objects.equals(getAlias(), animal.getAlias()) &&
                Objects.equals(getHuman(), animal.getHuman());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAlias(), getHuman());
    }
}
