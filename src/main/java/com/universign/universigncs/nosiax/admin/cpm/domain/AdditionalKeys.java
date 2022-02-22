package com.universign.universigncs.nosiax.admin.cpm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdditionalKeys.
 */
@Entity
@Table(name = "additional_keys")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdditionalKeys implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "jhi_key", nullable = false)
  private String key;

  @NotNull
  @Column(name = "value", nullable = false)
  private String value;

  @ManyToOne
  @JsonIgnoreProperties(value = { "voutchers", "additionalKeys" }, allowSetters = true)
  private RaRecord raRecord;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public AdditionalKeys id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getKey() {
    return this.key;
  }

  public AdditionalKeys key(String key) {
    this.setKey(key);
    return this;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return this.value;
  }

  public AdditionalKeys value(String value) {
    this.setValue(value);
    return this;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public RaRecord getRaRecord() {
    return this.raRecord;
  }

  public void setRaRecord(RaRecord raRecord) {
    this.raRecord = raRecord;
  }

  public AdditionalKeys raRecord(RaRecord raRecord) {
    this.setRaRecord(raRecord);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AdditionalKeys)) {
      return false;
    }
    return id != null && id.equals(((AdditionalKeys) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalKeys{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
