import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationImported, getOrganizationImportedIdentifier } from '../organization-imported.model';

export type EntityResponseType = HttpResponse<IOrganizationImported>;
export type EntityArrayResponseType = HttpResponse<IOrganizationImported[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationImportedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-importeds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organizationImported: IOrganizationImported): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationImported);
    return this.http
      .post<IOrganizationImported>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(organizationImported: IOrganizationImported): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationImported);
    return this.http
      .put<IOrganizationImported>(`${this.resourceUrl}/${getOrganizationImportedIdentifier(organizationImported) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(organizationImported: IOrganizationImported): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationImported);
    return this.http
      .patch<IOrganizationImported>(`${this.resourceUrl}/${getOrganizationImportedIdentifier(organizationImported) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrganizationImported>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrganizationImported[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrganizationImportedToCollectionIfMissing(
    organizationImportedCollection: IOrganizationImported[],
    ...organizationImportedsToCheck: (IOrganizationImported | null | undefined)[]
  ): IOrganizationImported[] {
    const organizationImporteds: IOrganizationImported[] = organizationImportedsToCheck.filter(isPresent);
    if (organizationImporteds.length > 0) {
      const organizationImportedCollectionIdentifiers = organizationImportedCollection.map(
        organizationImportedItem => getOrganizationImportedIdentifier(organizationImportedItem)!
      );
      const organizationImportedsToAdd = organizationImporteds.filter(organizationImportedItem => {
        const organizationImportedIdentifier = getOrganizationImportedIdentifier(organizationImportedItem);
        if (organizationImportedIdentifier == null || organizationImportedCollectionIdentifiers.includes(organizationImportedIdentifier)) {
          return false;
        }
        organizationImportedCollectionIdentifiers.push(organizationImportedIdentifier);
        return true;
      });
      return [...organizationImportedsToAdd, ...organizationImportedCollection];
    }
    return organizationImportedCollection;
  }

  protected convertDateFromClient(organizationImported: IOrganizationImported): IOrganizationImported {
    return Object.assign({}, organizationImported, {
      createDate: organizationImported.createDate?.isValid() ? organizationImported.createDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate ? dayjs(res.body.createDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((organizationImported: IOrganizationImported) => {
        organizationImported.createDate = organizationImported.createDate ? dayjs(organizationImported.createDate) : undefined;
      });
    }
    return res;
  }
}
