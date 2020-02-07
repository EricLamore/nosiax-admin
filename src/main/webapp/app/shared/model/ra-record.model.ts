import { Moment } from 'moment';
import { IVoucher } from 'app/shared/model/voucher.model';
import { IAdditionalKeys } from 'app/shared/model/additional-keys.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRaRecord {
  id?: number;
  status?: Status;
  idUser?: string;
  identifier?: string;
  certO?: string;
  commonName?: string;
  zipCode?: string;
  locality?: string;
  country?: string;
  lastname?: string;
  firstname?: string;
  email?: string;
  phone?: string;
  url?: string;
  idTransaction?: string;
  transactionStatus?: string;
  profilCpm?: string;
  reaso?: string;
  signatureDate?: Moment;
  validationDate?: Moment;
  voutchers?: IVoucher[];
  additionalKeys?: IAdditionalKeys[];
}

export class RaRecord implements IRaRecord {
  constructor(
    public id?: number,
    public status?: Status,
    public idUser?: string,
    public identifier?: string,
    public certO?: string,
    public commonName?: string,
    public zipCode?: string,
    public locality?: string,
    public country?: string,
    public lastname?: string,
    public firstname?: string,
    public email?: string,
    public phone?: string,
    public url?: string,
    public idTransaction?: string,
    public transactionStatus?: string,
    public profilCpm?: string,
    public reaso?: string,
    public signatureDate?: Moment,
    public validationDate?: Moment,
    public voutchers?: IVoucher[],
    public additionalKeys?: IAdditionalKeys[]
  ) {}
}
