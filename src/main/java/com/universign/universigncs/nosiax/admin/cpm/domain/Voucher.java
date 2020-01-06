package com.universign.universigncs.nosiax.admin.cpm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Voucher.
 */
@Entity
@Table(name = "voucher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @ManyToOne
    @JsonIgnoreProperties("voutchers")
    private RaRecord raRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public Voucher fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public Voucher key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RaRecord getRaRecord() {
        return raRecord;
    }

    public Voucher raRecord(RaRecord raRecord) {
        this.raRecord = raRecord;
        return this;
    }

    public void setRaRecord(RaRecord raRecord) {
        this.raRecord = raRecord;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voucher)) {
            return false;
        }
        return id != null && id.equals(((Voucher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", key='" + getKey() + "'" +
            "}";
    }
}
