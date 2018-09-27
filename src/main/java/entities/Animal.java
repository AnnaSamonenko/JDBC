package entities;

import java.util.Objects;

public class Animal {

    private int id;
    private String alias;
    private int human;

    public Animal() {
    }

    public Animal(int id, String alias, int human) {
        this.id = id;
        this.alias = alias;
        this.human = human;
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

    public int getHuman() {
        return human;
    }

    public void setHuman(int human) {
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
