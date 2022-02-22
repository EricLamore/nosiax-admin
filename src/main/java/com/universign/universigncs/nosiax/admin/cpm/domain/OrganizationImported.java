package com.universign.universigncs.nosiax.admin.cpm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.universign.universigncs.nosiax.admin.cpm.domain.enumeration.Language;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrganizationImported.
 */
@Entity
@Table(name = "organization_imported")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganizationImported implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "client")
  private String client;

  @Column(name = "displayname")
  private String displayname;

  @Column(name = "logo")
  private String logo;

  @Column(name = "profile_name")
  private String profileName;

  @Column(name = "conso_sig")
  private Boolean consoSig;

  @Column(name = "conso_times")
  private Boolean consoTimes;

  @Column(name = "conso_seal")
  private Boolean consoSeal;

  @Column(name = "technical_account_creation")
  private Boolean technicalAccountCreation;

  @Column(name = "is_technical_account_admin")
  private Boolean isTechnicalAccountAdmin;

  @Column(name = "is_technical_account")
  private Boolean isTechnicalAccount;

  @Column(name = "is_operator")
  private Boolean isOperator;

  @Column(name = "technical_account_firstname")
  private String technicalAccountFirstname;

  @Column(name = "technical_account_lastname")
  private String technicalAccountLastname;

  @Column(name = "technical_account_mail")
  private String technicalAccountMail;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  @Column(name = "org_created")
  private Boolean orgCreated;

  @Column(name = "technical_account_created")
  private Boolean technicalAccountCreated;

  @Column(name = "conso_created")
  private Boolean consoCreated;

  @Column(name = "create_date")
  private LocalDate createDate;

  @ManyToOne
  @JsonIgnoreProperties(value = { "organizationImporteds" }, allowSetters = true)
  private ImportOrganizations employee;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public OrganizationImported id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClient() {
    return this.client;
  }

  public OrganizationImported client(String client) {
    this.setClient(client);
    return this;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getDisplayname() {
    return this.displayname;
  }

  public OrganizationImported displayname(String displayname) {
    this.setDisplayname(displayname);
    return this;
  }

  public void setDisplayname(String displayname) {
    this.displayname = displayname;
  }

  public String getLogo() {
    return this.logo;
  }

  public OrganizationImported logo(String logo) {
    this.setLogo(logo);
    return this;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getProfileName() {
    return this.profileName;
  }

  public OrganizationImported profileName(String profileName) {
    this.setProfileName(profileName);
    return this;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public Boolean getConsoSig() {
    return this.consoSig;
  }

  public OrganizationImported consoSig(Boolean consoSig) {
    this.setConsoSig(consoSig);
    return this;
  }

  public void setConsoSig(Boolean consoSig) {
    this.consoSig = consoSig;
  }

  public Boolean getConsoTimes() {
    return this.consoTimes;
  }

  public OrganizationImported consoTimes(Boolean consoTimes) {
    this.setConsoTimes(consoTimes);
    return this;
  }

  public void setConsoTimes(Boolean consoTimes) {
    this.consoTimes = consoTimes;
  }

  public Boolean getConsoSeal() {
    return this.consoSeal;
  }

  public OrganizationImported consoSeal(Boolean consoSeal) {
    this.setConsoSeal(consoSeal);
    return this;
  }

  public void setConsoSeal(Boolean consoSeal) {
    this.consoSeal = consoSeal;
  }

  public Boolean getTechnicalAccountCreation() {
    return this.technicalAccountCreation;
  }

  public OrganizationImported technicalAccountCreation(Boolean technicalAccountCreation) {
    this.setTechnicalAccountCreation(technicalAccountCreation);
    return this;
  }

  public void setTechnicalAccountCreation(Boolean technicalAccountCreation) {
    this.technicalAccountCreation = technicalAccountCreation;
  }

  public Boolean getIsTechnicalAccountAdmin() {
    return this.isTechnicalAccountAdmin;
  }

  public OrganizationImported isTechnicalAccountAdmin(Boolean isTechnicalAccountAdmin) {
    this.setIsTechnicalAccountAdmin(isTechnicalAccountAdmin);
    return this;
  }

  public void setIsTechnicalAccountAdmin(Boolean isTechnicalAccountAdmin) {
    this.isTechnicalAccountAdmin = isTechnicalAccountAdmin;
  }

  public Boolean getIsTechnicalAccount() {
    return this.isTechnicalAccount;
  }

  public OrganizationImported isTechnicalAccount(Boolean isTechnicalAccount) {
    this.setIsTechnicalAccount(isTechnicalAccount);
    return this;
  }

  public void setIsTechnicalAccount(Boolean isTechnicalAccount) {
    this.isTechnicalAccount = isTechnicalAccount;
  }

  public Boolean getIsOperator() {
    return this.isOperator;
  }

  public OrganizationImported isOperator(Boolean isOperator) {
    this.setIsOperator(isOperator);
    return this;
  }

  public void setIsOperator(Boolean isOperator) {
    this.isOperator = isOperator;
  }

  public String getTechnicalAccountFirstname() {
    return this.technicalAccountFirstname;
  }

  public OrganizationImported technicalAccountFirstname(String technicalAccountFirstname) {
    this.setTechnicalAccountFirstname(technicalAccountFirstname);
    return this;
  }

  public void setTechnicalAccountFirstname(String technicalAccountFirstname) {
    this.technicalAccountFirstname = technicalAccountFirstname;
  }

  public String getTechnicalAccountLastname() {
    return this.technicalAccountLastname;
  }

  public OrganizationImported technicalAccountLastname(String technicalAccountLastname) {
    this.setTechnicalAccountLastname(technicalAccountLastname);
    return this;
  }

  public void setTechnicalAccountLastname(String technicalAccountLastname) {
    this.technicalAccountLastname = technicalAccountLastname;
  }

  public String getTechnicalAccountMail() {
    return this.technicalAccountMail;
  }

  public OrganizationImported technicalAccountMail(String technicalAccountMail) {
    this.setTechnicalAccountMail(technicalAccountMail);
    return this;
  }

  public void setTechnicalAccountMail(String technicalAccountMail) {
    this.technicalAccountMail = technicalAccountMail;
  }

  public Language getLanguage() {
    return this.language;
  }

  public OrganizationImported language(Language language) {
    this.setLanguage(language);
    return this;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Boolean getOrgCreated() {
    return this.orgCreated;
  }

  public OrganizationImported orgCreated(Boolean orgCreated) {
    this.setOrgCreated(orgCreated);
    return this;
  }

  public void setOrgCreated(Boolean orgCreated) {
    this.orgCreated = orgCreated;
  }

  public Boolean getTechnicalAccountCreated() {
    return this.technicalAccountCreated;
  }

  public OrganizationImported technicalAccountCreated(Boolean technicalAccountCreated) {
    this.setTechnicalAccountCreated(technicalAccountCreated);
    return this;
  }

  public void setTechnicalAccountCreated(Boolean technicalAccountCreated) {
    this.technicalAccountCreated = technicalAccountCreated;
  }

  public Boolean getConsoCreated() {
    return this.consoCreated;
  }

  public OrganizationImported consoCreated(Boolean consoCreated) {
    this.setConsoCreated(consoCreated);
    return this;
  }

  public void setConsoCreated(Boolean consoCreated) {
    this.consoCreated = consoCreated;
  }

  public LocalDate getCreateDate() {
    return this.createDate;
  }

  public OrganizationImported createDate(LocalDate createDate) {
    this.setCreateDate(createDate);
    return this;
  }

  public void setCreateDate(LocalDate createDate) {
    this.createDate = createDate;
  }

  public ImportOrganizations getEmployee() {
    return this.employee;
  }

  public void setEmployee(ImportOrganizations importOrganizations) {
    this.employee = importOrganizations;
  }

  public OrganizationImported employee(ImportOrganizations importOrganizations) {
    this.setEmployee(importOrganizations);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrganizationImported)) {
      return false;
    }
    return id != null && id.equals(((OrganizationImported) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationImported{" +
            "id=" + getId() +
            ", client='" + getClient() + "'" +
            ", displayname='" + getDisplayname() + "'" +
            ", logo='" + getLogo() + "'" +
            ", profileName='" + getProfileName() + "'" +
            ", consoSig='" + getConsoSig() + "'" +
            ", consoTimes='" + getConsoTimes() + "'" +
            ", consoSeal='" + getConsoSeal() + "'" +
            ", technicalAccountCreation='" + getTechnicalAccountCreation() + "'" +
            ", isTechnicalAccountAdmin='" + getIsTechnicalAccountAdmin() + "'" +
            ", isTechnicalAccount='" + getIsTechnicalAccount() + "'" +
            ", isOperator='" + getIsOperator() + "'" +
            ", technicalAccountFirstname='" + getTechnicalAccountFirstname() + "'" +
            ", technicalAccountLastname='" + getTechnicalAccountLastname() + "'" +
            ", technicalAccountMail='" + getTechnicalAccountMail() + "'" +
            ", language='" + getLanguage() + "'" +
            ", orgCreated='" + getOrgCreated() + "'" +
            ", technicalAccountCreated='" + getTechnicalAccountCreated() + "'" +
            ", consoCreated='" + getConsoCreated() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
