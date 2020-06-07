package com.myservice.mainpack.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;


@Entity
@Table(name = "DOCUMENTS", uniqueConstraints = {@UniqueConstraint(columnNames = "SRC", name = "documents_unique_src_idx")})
public class Document extends AbstractBaseEntity {

    @Column(name = "SRC")
    private String src;



    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }


}
