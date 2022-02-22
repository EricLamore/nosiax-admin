import dayjs from 'dayjs/esm';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { IAdditionalKeys } from 'app/entities/additional-keys/additional-keys.model';
import { Status } from 'app/entities/enumerations/status.model';

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
  url?: string | null;
  idTransaction?: string | null;
  transactionStatus?: string | null;
  profilCpm?: string | null;
  reaso?: string | null;
  signatureDate?: dayjs.Dayjs | null;
  validationDate?: dayjs.Dayjs | null;
  voutchers?: IVoucher[] | null;
  additionalKeys?: IAdditionalKeys[] | null;
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
    public url?: string | null,
    public idTransaction?: string | null,
    public transactionStatus?: string | null,
    public profilCpm?: string | null,
    public reaso?: string | null,
    public signatureDate?: dayjs.Dayjs | null,
    public validationDate?: dayjs.Dayjs | null,
    public voutchers?: IVoucher[] | null,
    public additionalKeys?: IAdditionalKeys[] | null
  ) {}
}

export function getRaRecordIdentifier(raRecord: IRaRecord): number | undefined {
  return raRecord.id;
}
