import { IRaRecord } from 'app/entities/ra-record/ra-record.model';

export interface IAdditionalKeys {
  id?: number;
  key?: string;
  value?: string;
  raRecord?: IRaRecord | null;
}

export class AdditionalKeys implements IAdditionalKeys {
  constructor(public id?: number, public key?: string, public value?: string, public raRecord?: IRaRecord | null) {}
}

export function getAdditionalKeysIdentifier(additionalKeys: IAdditionalKeys): number | undefined {
  return additionalKeys.id;
}
