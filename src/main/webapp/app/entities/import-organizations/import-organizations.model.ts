import dayjs from 'dayjs/esm';
import { IOrganizationImported } from 'app/entities/organization-imported/organization-imported.model';

export interface IImportOrganizations {
  id?: number;
  orgMasterId?: string | null;
  orgName?: string | null;
  partenaire?: string | null;
  profile?: string | null;
  launchCreationDate?: dayjs.Dayjs | null;
  organizationImporteds?: IOrganizationImported[] | null;
}

export class ImportOrganizations implements IImportOrganizations {
  constructor(
    public id?: number,
    public orgMasterId?: string | null,
    public orgName?: string | null,
    public partenaire?: string | null,
    public profile?: string | null,
    public launchCreationDate?: dayjs.Dayjs | null,
    public organizationImporteds?: IOrganizationImported[] | null
  ) {}
}

export function getImportOrganizationsIdentifier(importOrganizations: IImportOrganizations): number | undefined {
  return importOrganizations.id;
}
