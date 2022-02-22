import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRaRecord, getRaRecordIdentifier } from '../ra-record.model';

export type EntityResponseType = HttpResponse<IRaRecord>;
export type EntityArrayResponseType = HttpResponse<IRaRecord[]>;

@Injectable({ providedIn: 'root' })
export class RaRecordService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ra-records');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(raRecord: IRaRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(raRecord);
    return this.http
      .post<IRaRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(raRecord: IRaRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(raRecord);
    return this.http
      .put<IRaRecord>(`${this.resourceUrl}/${getRaRecordIdentifier(raRecord) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(raRecord: IRaRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(raRecord);
    return this.http
      .patch<IRaRecord>(`${this.resourceUrl}/${getRaRecordIdentifier(raRecord) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRaRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRaRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRaRecordToCollectionIfMissing(raRecordCollection: IRaRecord[], ...raRecordsToCheck: (IRaRecord | null | undefined)[]): IRaRecord[] {
    const raRecords: IRaRecord[] = raRecordsToCheck.filter(isPresent);
    if (raRecords.length > 0) {
      const raRecordCollectionIdentifiers = raRecordCollection.map(raRecordItem => getRaRecordIdentifier(raRecordItem)!);
      const raRecordsToAdd = raRecords.filter(raRecordItem => {
        const raRecordIdentifier = getRaRecordIdentifier(raRecordItem);
        if (raRecordIdentifier == null || raRecordCollectionIdentifiers.includes(raRecordIdentifier)) {
          return false;
        }
        raRecordCollectionIdentifiers.push(raRecordIdentifier);
        return true;
      });
      return [...raRecordsToAdd, ...raRecordCollection];
    }
    return raRecordCollection;
  }

  protected convertDateFromClient(raRecord: IRaRecord): IRaRecord {
    return Object.assign({}, raRecord, {
      signatureDate: raRecord.signatureDate?.isValid() ? raRecord.signatureDate.toJSON() : undefined,
      validationDate: raRecord.validationDate?.isValid() ? raRecord.validationDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.signatureDate = res.body.signatureDate ? dayjs(res.body.signatureDate) : undefined;
      res.body.validationDate = res.body.validationDate ? dayjs(res.body.validationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((raRecord: IRaRecord) => {
        raRecord.signatureDate = raRecord.signatureDate ? dayjs(raRecord.signatureDate) : undefined;
        raRecord.validationDate = raRecord.validationDate ? dayjs(raRecord.validationDate) : undefined;
      });
    }
    return res;
  }
}
