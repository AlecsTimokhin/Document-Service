package com.myservice.mainpack.model;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Persistable;
import javax.persistence.*;


@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity implements Persistable<Long> {

    //@Value("${GLOBAL_SEQ_START_VALUE}")
    //private static final int GLOBAL_SEQ_START_VALUE = 0;

    @Id
    //@SequenceGenerator(name = "GLOBAL_SEQ", sequenceName = "GLOBAL_SEQ", allocationSize = 1, initialValue = AbstractBaseEntity.GLOBAL_SEQ_START_VALUE)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    protected AbstractBaseEntity() {}


    protected AbstractBaseEntity(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) { return false; }
        AbstractBaseEntity abstractBaseEntity = (AbstractBaseEntity) o;
        return id != null && id.equals(abstractBaseEntity.id);
    }

    @Override
    public int hashCode() {
        //return id == null ? -1 : id;
        return id == null ? -1 : Integer.parseInt(id + "");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id = " + id + ")";
    }


}