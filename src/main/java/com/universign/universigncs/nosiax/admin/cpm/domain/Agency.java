package com.universign.universigncs.nosiax.admin.cpm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Agency.
 */
@Entity
@Table(name = "agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idx_agency")
    private Integer idxAgency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdxAgency() {
        return idxAgency;
    }

    public Agency idxAgency(Integer idxAgency) {
        this.idxAgency = idxAgency;
        return this;
    }

    public void setIdxAgency(Integer idxAgency) {
        this.idxAgency = idxAgency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agency)) {
            return false;
        }
        return id != null && id.equals(((Agency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Agency{" +
            "id=" + getId() +
            ", idxAgency=" + getIdxAgency() +
            "}";
    }
}
