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
  locality?: string;
  country?: string;
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
    public locality?: string,
    public country?: string,
    public voutchers?: IVoucher[],
    public additionalKeys?: IAdditionalKeys[]
  ) {}
}
