import { IRaRecord } from 'app/entities/ra-record/ra-record.model';

export interface IVoucher {
  id?: number;
  fileName?: string;
  key?: string;
  raRecord?: IRaRecord | null;
}

export class Voucher implements IVoucher {
  constructor(public id?: number, public fileName?: string, public key?: string, public raRecord?: IRaRecord | null) {}
}

export function getVoucherIdentifier(voucher: IVoucher): number | undefined {
  return voucher.id;
}
