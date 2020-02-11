import { Moment } from 'moment';
import { IAgency } from 'app/shared/model/agency.model';
import { ClosingStatus } from 'app/shared/model/enumerations/closing-status.model';

export interface IClosingRequest {
  id?: number;
  idxAgency?: number;
  closingStatus?: ClosingStatus;
  closingDate?: Moment;
  revokeCertificate?: boolean;
  revokeCertificateDate?: Moment;
  linkEsign?: boolean;
  linkEsignDate?: Moment;
  anonymized?: boolean;
  anonymizedDate?: Moment;
  createBill?: boolean;
  createBillDate?: Moment;
  sendBill?: boolean;
  sendBillDate?: Moment;
  terminate?: boolean;
  terminateDate?: Moment;
  idTransactionClosing?: string;
  urlTransactionClosing?: string;
  agency?: IAgency;
}

export class ClosingRequest implements IClosingRequest {
  constructor(
    public id?: number,
    public idxAgency?: number,
    public closingStatus?: ClosingStatus,
    public closingDate?: Moment,
    public revokeCertificate?: boolean,
    public revokeCertificateDate?: Moment,
    public linkEsign?: boolean,
    public linkEsignDate?: Moment,
    public anonymized?: boolean,
    public anonymizedDate?: Moment,
    public createBill?: boolean,
    public createBillDate?: Moment,
    public sendBill?: boolean,
    public sendBillDate?: Moment,
    public terminate?: boolean,
    public terminateDate?: Moment,
    public idTransactionClosing?: string,
    public urlTransactionClosing?: string,
    public agency?: IAgency
  ) {
    this.revokeCertificate = this.revokeCertificate || false;
    this.linkEsign = this.linkEsign || false;
    this.anonymized = this.anonymized || false;
    this.createBill = this.createBill || false;
    this.sendBill = this.sendBill || false;
    this.terminate = this.terminate || false;
  }
}
