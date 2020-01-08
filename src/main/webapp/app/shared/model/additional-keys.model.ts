import { IRaRecord } from 'app/shared/model/ra-record.model';

export interface IAdditionalKeys {
  id?: number;
  key?: string;
  value?: string;
  raRecord?: IRaRecord;
}

export class AdditionalKeys implements IAdditionalKeys {
  constructor(public id?: number, public key?: string, public value?: string, public raRecord?: IRaRecord) {}
}
