package com.gmail.mxwild.mealcalories.model;

public abstract class NamedEntity extends BaseEntity {

    private String name;

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    protected NamedEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + name + ")";
    }
}
