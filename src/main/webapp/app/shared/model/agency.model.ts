export interface IAgency {
  id?: number;
  idxAgency?: number;
}

export class Agency implements IAgency {
  constructor(public id?: number, public idxAgency?: number) {}
}
