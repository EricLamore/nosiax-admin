import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImportOrganizations, getImportOrganizationsIdentifier } from '../import-organizations.model';

export type EntityResponseType = HttpResponse<IImportOrganizations>;
export type EntityArrayResponseType = HttpResponse<IImportOrganizations[]>;

@Injectable({ providedIn: 'root' })
export class ImportOrganizationsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/import-organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(importOrganizations: IImportOrganizations): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(importOrganizations);
    return this.http
      .post<IImportOrganizations>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(importOrganizations: IImportOrganizations): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(importOrganizations);
    return this.http
      .put<IImportOrganizations>(`${this.resourceUrl}/${getImportOrganizationsIdentifier(importOrganizations) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(importOrganizations: IImportOrganizations): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(importOrganizations);
    return this.http
      .patch<IImportOrganizations>(`${this.resourceUrl}/${getImportOrganizationsIdentifier(importOrganizations) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImportOrganizations>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImportOrganizations[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addImportOrganizationsToCollectionIfMissing(
    importOrganizationsCollection: IImportOrganizations[],
    ...importOrganizationsToCheck: (IImportOrganizations | null | undefined)[]
  ): IImportOrganizations[] {
    const importOrganizations: IImportOrganizations[] = importOrganizationsToCheck.filter(isPresent);
    if (importOrganizations.length > 0) {
      const importOrganizationsCollectionIdentifiers = importOrganizationsCollection.map(
        importOrganizationsItem => getImportOrganizationsIdentifier(importOrganizationsItem)!
      );
      const importOrganizationsToAdd = importOrganizations.filter(importOrganizationsItem => {
        const importOrganizationsIdentifier = getImportOrganizationsIdentifier(importOrganizationsItem);
        if (importOrganizationsIdentifier == null || importOrganizationsCollectionIdentifiers.includes(importOrganizationsIdentifier)) {
          return false;
        }
        importOrganizationsCollectionIdentifiers.push(importOrganizationsIdentifier);
        return true;
      });
      return [...importOrganizationsToAdd, ...importOrganizationsCollection];
    }
    return importOrganizationsCollection;
  }

  protected convertDateFromClient(importOrganizations: IImportOrganizations): IImportOrganizations {
    return Object.assign({}, importOrganizations, {
      launchCreationDate: importOrganizations.launchCreationDate?.isValid()
        ? importOrganizations.launchCreationDate.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.launchCreationDate = res.body.launchCreationDate ? dayjs(res.body.launchCreationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((importOrganizations: IImportOrganizations) => {
        importOrganizations.launchCreationDate = importOrganizations.launchCreationDate
          ? dayjs(importOrganizations.launchCreationDate)
          : undefined;
      });
    }
    return res;
  }
}
