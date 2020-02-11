package com.universign.universigncs.nosiax.admin.cpm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
    private ZonedDateTime closingDate;

    @Column(name = "revoke_certificate")
    private Boolean revokeCertificate;

    @Column(name = "revoke_certificate_date")
    private ZonedDateTime revokeCertificateDate;

    @Column(name = "link_esign")
    private Boolean linkEsign;

    @Column(name = "link_esign_date")
    private ZonedDateTime linkEsignDate;

    @Column(name = "anonymized")
    private Boolean anonymized;

    @Column(name = "anonymized_date")
    private ZonedDateTime anonymizedDate;

    @Column(name = "create_bill")
    private Boolean createBill;

    @Column(name = "create_bill_date")
    private ZonedDateTime createBillDate;

    @Column(name = "send_bill")
    private Boolean sendBill;

    @Column(name = "send_bill_date")
    private ZonedDateTime sendBillDate;

    @Column(name = "terminate")
    private Boolean terminate;

    @Column(name = "terminate_date")
    private ZonedDateTime terminateDate;

    @Column(name = "id_transaction_closing")
    private String idTransactionClosing;

    @Column(name = "url_transaction_closing")
    private String urlTransactionClosing;

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

    public ZonedDateTime getClosingDate() {
        return closingDate;
    }

    public ClosingRequest closingDate(ZonedDateTime closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public void setClosingDate(ZonedDateTime closingDate) {
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

    public ZonedDateTime getRevokeCertificateDate() {
        return revokeCertificateDate;
    }

    public ClosingRequest revokeCertificateDate(ZonedDateTime revokeCertificateDate) {
        this.revokeCertificateDate = revokeCertificateDate;
        return this;
    }

    public void setRevokeCertificateDate(ZonedDateTime revokeCertificateDate) {
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

    public ZonedDateTime getLinkEsignDate() {
        return linkEsignDate;
    }

    public ClosingRequest linkEsignDate(ZonedDateTime linkEsignDate) {
        this.linkEsignDate = linkEsignDate;
        return this;
    }

    public void setLinkEsignDate(ZonedDateTime linkEsignDate) {
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

    public ZonedDateTime getAnonymizedDate() {
        return anonymizedDate;
    }

    public ClosingRequest anonymizedDate(ZonedDateTime anonymizedDate) {
        this.anonymizedDate = anonymizedDate;
        return this;
    }

    public void setAnonymizedDate(ZonedDateTime anonymizedDate) {
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

    public ZonedDateTime getCreateBillDate() {
        return createBillDate;
    }

    public ClosingRequest createBillDate(ZonedDateTime createBillDate) {
        this.createBillDate = createBillDate;
        return this;
    }

    public void setCreateBillDate(ZonedDateTime createBillDate) {
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

    public ZonedDateTime getSendBillDate() {
        return sendBillDate;
    }

    public ClosingRequest sendBillDate(ZonedDateTime sendBillDate) {
        this.sendBillDate = sendBillDate;
        return this;
    }

    public void setSendBillDate(ZonedDateTime sendBillDate) {
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

    public ZonedDateTime getTerminateDate() {
        return terminateDate;
    }

    public ClosingRequest terminateDate(ZonedDateTime terminateDate) {
        this.terminateDate = terminateDate;
        return this;
    }

    public void setTerminateDate(ZonedDateTime terminateDate) {
        this.terminateDate = terminateDate;
    }

    public String getIdTransactionClosing() {
        return idTransactionClosing;
    }

    public ClosingRequest idTransactionClosing(String idTransactionClosing) {
        this.idTransactionClosing = idTransactionClosing;
        return this;
    }

    public void setIdTransactionClosing(String idTransactionClosing) {
        this.idTransactionClosing = idTransactionClosing;
    }

    public String getUrlTransactionClosing() {
        return urlTransactionClosing;
    }

    public ClosingRequest urlTransactionClosing(String urlTransactionClosing) {
        this.urlTransactionClosing = urlTransactionClosing;
        return this;
    }

    public void setUrlTransactionClosing(String urlTransactionClosing) {
        this.urlTransactionClosing = urlTransactionClosing;
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
            ", idTransactionClosing='" + getIdTransactionClosing() + "'" +
            ", urlTransactionClosing='" + getUrlTransactionClosing() + "'" +
            "}";
    }
}
