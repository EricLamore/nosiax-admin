package com.universign.universigncs.nosiax.admin.cpm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Employee entity.
 */
@Schema(description = "The Employee entity.")
@Entity
@Table(name = "import_organizations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ImportOrganizations implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The firstname attribute.
   */
  @Schema(description = "The firstname attribute.")
  @Column(name = "org_master_id")
  private String orgMasterId;

  @Column(name = "org_name")
  private String orgName;

  @Column(name = "partenaire")
  private String partenaire;

  @Column(name = "profile")
  private String profile;

  @Column(name = "launch_creation_date")
  private LocalDate launchCreationDate;

  @OneToMany(mappedBy = "employee")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
  private Set<OrganizationImported> organizationImporteds = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ImportOrganizations id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrgMasterId() {
    return this.orgMasterId;
  }

  public ImportOrganizations orgMasterId(String orgMasterId) {
    this.setOrgMasterId(orgMasterId);
    return this;
  }

  public void setOrgMasterId(String orgMasterId) {
    this.orgMasterId = orgMasterId;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public ImportOrganizations orgName(String orgName) {
    this.setOrgName(orgName);
    return this;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getPartenaire() {
    return this.partenaire;
  }

  public ImportOrganizations partenaire(String partenaire) {
    this.setPartenaire(partenaire);
    return this;
  }

  public void setPartenaire(String partenaire) {
    this.partenaire = partenaire;
  }

  public String getProfile() {
    return this.profile;
  }

  public ImportOrganizations profile(String profile) {
    this.setProfile(profile);
    return this;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  public LocalDate getLaunchCreationDate() {
    return this.launchCreationDate;
  }

  public ImportOrganizations launchCreationDate(LocalDate launchCreationDate) {
    this.setLaunchCreationDate(launchCreationDate);
    return this;
  }

  public void setLaunchCreationDate(LocalDate launchCreationDate) {
    this.launchCreationDate = launchCreationDate;
  }

  public Set<OrganizationImported> getOrganizationImporteds() {
    return this.organizationImporteds;
  }

  public void setOrganizationImporteds(Set<OrganizationImported> organizationImporteds) {
    if (this.organizationImporteds != null) {
      this.organizationImporteds.forEach(i -> i.setEmployee(null));
    }
    if (organizationImporteds != null) {
      organizationImporteds.forEach(i -> i.setEmployee(this));
    }
    this.organizationImporteds = organizationImporteds;
  }

  public ImportOrganizations organizationImporteds(Set<OrganizationImported> organizationImporteds) {
    this.setOrganizationImporteds(organizationImporteds);
    return this;
  }

  public ImportOrganizations addOrganizationImported(OrganizationImported organizationImported) {
    this.organizationImporteds.add(organizationImported);
    organizationImported.setEmployee(this);
    return this;
  }

  public ImportOrganizations removeOrganizationImported(OrganizationImported organizationImported) {
    this.organizationImporteds.remove(organizationImported);
    organizationImported.setEmployee(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ImportOrganizations)) {
      return false;
    }
    return id != null && id.equals(((ImportOrganizations) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ImportOrganizations{" +
            "id=" + getId() +
            ", orgMasterId='" + getOrgMasterId() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", partenaire='" + getPartenaire() + "'" +
            ", profile='" + getProfile() + "'" +
            ", launchCreationDate='" + getLaunchCreationDate() + "'" +
            "}";
    }
}
