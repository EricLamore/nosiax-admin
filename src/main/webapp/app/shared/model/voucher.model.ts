import { IRaRecord } from 'app/shared/model/ra-record.model';

export interface IVoucher {
  id?: number;
  fileName?: string;
  key?: string;
  raRecord?: IRaRecord;
}

export class Voucher implements IVoucher {
  constructor(public id?: number, public fileName?: string, public key?: string, public raRecord?: IRaRecord) {}
}
