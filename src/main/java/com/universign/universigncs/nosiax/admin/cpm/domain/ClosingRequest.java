package com.universign.universigncs.nosiax.admin.cpm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.universign.universigncs.nosiax.admin.cpm.domain.enumeration.ClosingStatus;

/**
 * A ClosingRequest.
 */
@Entity
@Table(name = "closing_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClosingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idx_agency")
    private Integer idxAgency;

    @Enumerated(EnumType.STRING)
    @Column(name = "closing_status")
    private ClosingStatus closingStatus;

    @Column(name = "closing_date")
    private Instant closingDate;

    @Column(name = "revoke_certificate")
    private Boolean revokeCertificate;

    @Column(name = "revoke_certificate_date")
    private Instant revokeCertificateDate;

    @Column(name = "link_esign")
    private Boolean linkEsign;

    @Column(name = "link_esign_date")
    private Instant linkEsignDate;

    @Column(name = "anonymized")
    private Boolean anonymized;

    @Column(name = "anonymized_date")
    private Instant anonymizedDate;

    @Column(name = "create_bill")
    private Boolean createBill;

    @Column(name = "create_bill_date")
    private Instant createBillDate;

    @Column(name = "send_bill")
    private Boolean sendBill;

    @Column(name = "send_bill_date")
    private Instant sendBillDate;

    @Column(name = "terminate")
    private Boolean terminate;

    @Column(name = "terminate_date")
    private Instant terminateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Agency agency;

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

    public ClosingRequest idxAgency(Integer idxAgency) {
        this.idxAgency = idxAgency;
        return this;
    }

    public void setIdxAgency(Integer idxAgency) {
        this.idxAgency = idxAgency;
    }

    public ClosingStatus getClosingStatus() {
        return closingStatus;
    }

    public ClosingRequest closingStatus(ClosingStatus closingStatus) {
        this.closingStatus = closingStatus;
        return this;
    }

    public void setClosingStatus(ClosingStatus closingStatus) {
        this.closingStatus = closingStatus;
    }

    public Instant getClosingDate() {
        return closingDate;
    }

    public ClosingRequest closingDate(Instant closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public void setClosingDate(Instant closingDate) {
        this.closingDate = closingDate;
    }

    public Boolean isRevokeCertificate() {
        return revokeCertificate;
    }

    public ClosingRequest revokeCertificate(Boolean revokeCertificate) {
        this.revokeCertificate = revokeCertificate;
        return this;
    }

    public void setRevokeCertificate(Boolean revokeCertificate) {
        this.revokeCertificate = revokeCertificate;
    }

    public Instant getRevokeCertificateDate() {
        return revokeCertificateDate;
    }

    public ClosingRequest revokeCertificateDate(Instant revokeCertificateDate) {
        this.revokeCertificateDate = revokeCertificateDate;
        return this;
    }

    public void setRevokeCertificateDate(Instant revokeCertificateDate) {
        this.revokeCertificateDate = revokeCertificateDate;
    }

    public Boolean isLinkEsign() {
        return linkEsign;
    }

    public ClosingRequest linkEsign(Boolean linkEsign) {
        this.linkEsign = linkEsign;
        return this;
    }

    public void setLinkEsign(Boolean linkEsign) {
        this.linkEsign = linkEsign;
    }

    public Instant getLinkEsignDate() {
        return linkEsignDate;
    }

    public ClosingRequest linkEsignDate(Instant linkEsignDate) {
        this.linkEsignDate = linkEsignDate;
        return this;
    }

    public void setLinkEsignDate(Instant linkEsignDate) {
        this.linkEsignDate = linkEsignDate;
    }

    public Boolean isAnonymized() {
        return anonymized;
    }

    public ClosingRequest anonymized(Boolean anonymized) {
        this.anonymized = anonymized;
        return this;
    }

    public void setAnonymized(Boolean anonymized) {
        this.anonymized = anonymized;
    }

    public Instant getAnonymizedDate() {
        return anonymizedDate;
    }

    public ClosingRequest anonymizedDate(Instant anonymizedDate) {
        this.anonymizedDate = anonymizedDate;
        return this;
    }

    public void setAnonymizedDate(Instant anonymizedDate) {
        this.anonymizedDate = anonymizedDate;
    }

    public Boolean isCreateBill() {
        return createBill;
    }

    public ClosingRequest createBill(Boolean createBill) {
        this.createBill = createBill;
        return this;
    }

    public void setCreateBill(Boolean createBill) {
        this.createBill = createBill;
    }

    public Instant getCreateBillDate() {
        return createBillDate;
    }

    public ClosingRequest createBillDate(Instant createBillDate) {
        this.createBillDate = createBillDate;
        return this;
    }

    public void setCreateBillDate(Instant createBillDate) {
        this.createBillDate = createBillDate;
    }

    public Boolean isSendBill() {
        return sendBill;
    }

    public ClosingRequest sendBill(Boolean sendBill) {
        this.sendBill = sendBill;
        return this;
    }

    public void setSendBill(Boolean sendBill) {
        this.sendBill = sendBill;
    }

    public Instant getSendBillDate() {
        return sendBillDate;
    }

    public ClosingRequest sendBillDate(Instant sendBillDate) {
        this.sendBillDate = sendBillDate;
        return this;
    }

    public void setSendBillDate(Instant sendBillDate) {
        this.sendBillDate = sendBillDate;
    }

    public Boolean isTerminate() {
        return terminate;
    }

    public ClosingRequest terminate(Boolean terminate) {
        this.terminate = terminate;
        return this;
    }

    public void setTerminate(Boolean terminate) {
        this.terminate = terminate;
    }

    public Instant getTerminateDate() {
        return terminateDate;
    }

    public ClosingRequest terminateDate(Instant terminateDate) {
        this.terminateDate = terminateDate;
        return this;
    }

    public void setTerminateDate(Instant terminateDate) {
        this.terminateDate = terminateDate;
    }

    public Agency getAgency() {
        return agency;
    }

    public ClosingRequest agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClosingRequest)) {
            return false;
        }
        return id != null && id.equals(((ClosingRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClosingRequest{" +
            "id=" + getId() +
            ", idxAgency=" + getIdxAgency() +
            ", closingStatus='" + getClosingStatus() + "'" +
            ", closingDate='" + getClosingDate() + "'" +
            ", revokeCertificate='" + isRevokeCertificate() + "'" +
            ", revokeCertificateDate='" + getRevokeCertificateDate() + "'" +
            ", linkEsign='" + isLinkEsign() + "'" +
            ", linkEsignDate='" + getLinkEsignDate() + "'" +
            ", anonymized='" + isAnonymized() + "'" +
            ", anonymizedDate='" + getAnonymizedDate() + "'" +
            ", createBill='" + isCreateBill() + "'" +
            ", createBillDate='" + getCreateBillDate() + "'" +
            ", sendBill='" + isSendBill() + "'" +
            ", sendBillDate='" + getSendBillDate() + "'" +
            ", terminate='" + isTerminate() + "'" +
            ", terminateDate='" + getTerminateDate() + "'" +
            "}";
    }
}
