package com.gmail.mxwild.mealcalories.model;



import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class NamedEntity extends BaseEntity {

    @NotBlank
    @Size(min = 2, max = 180)
    @Column(name = "name", nullable = false)
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
