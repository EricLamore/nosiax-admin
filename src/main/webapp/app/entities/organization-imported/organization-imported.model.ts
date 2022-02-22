import dayjs from 'dayjs/esm';
import { IImportOrganizations } from 'app/entities/import-organizations/import-organizations.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IOrganizationImported {
  id?: number;
  client?: string | null;
  displayname?: string | null;
  logo?: string | null;
  profileName?: string | null;
  consoSig?: boolean | null;
  consoTimes?: boolean | null;
  consoSeal?: boolean | null;
  technicalAccountCreation?: boolean | null;
  isTechnicalAccountAdmin?: boolean | null;
  isTechnicalAccount?: boolean | null;
  isOperator?: boolean | null;
  technicalAccountFirstname?: string | null;
  technicalAccountLastname?: string | null;
  technicalAccountMail?: string | null;
  language?: Language | null;
  orgCreated?: boolean | null;
  technicalAccountCreated?: boolean | null;
  consoCreated?: boolean | null;
  createDate?: dayjs.Dayjs | null;
  organizationImported?: IImportOrganizations | null;
}

export class OrganizationImported implements IOrganizationImported {
  constructor(
    public id?: number,
    public client?: string | null,
    public displayname?: string | null,
    public logo?: string | null,
    public profileName?: string | null,
    public consoSig?: boolean | null,
    public consoTimes?: boolean | null,
    public consoSeal?: boolean | null,
    public technicalAccountCreation?: boolean | null,
    public isTechnicalAccountAdmin?: boolean | null,
    public isTechnicalAccount?: boolean | null,
    public isOperator?: boolean | null,
    public technicalAccountFirstname?: string | null,
    public technicalAccountLastname?: string | null,
    public technicalAccountMail?: string | null,
    public language?: Language | null,
    public orgCreated?: boolean | null,
    public technicalAccountCreated?: boolean | null,
    public consoCreated?: boolean | null,
    public createDate?: dayjs.Dayjs | null,
    public organizationImported?: IImportOrganizations | null
  ) {
    this.consoSig = this.consoSig ?? false;
    this.consoTimes = this.consoTimes ?? false;
    this.consoSeal = this.consoSeal ?? false;
    this.technicalAccountCreation = this.technicalAccountCreation ?? false;
    this.isTechnicalAccountAdmin = this.isTechnicalAccountAdmin ?? false;
    this.isTechnicalAccount = this.isTechnicalAccount ?? false;
    this.isOperator = this.isOperator ?? false;
    this.orgCreated = this.orgCreated ?? false;
    this.technicalAccountCreated = this.technicalAccountCreated ?? false;
    this.consoCreated = this.consoCreated ?? false;
  }
}

export function getOrganizationImportedIdentifier(organizationImported: IOrganizationImported): number | undefined {
  return organizationImported.id;
}
