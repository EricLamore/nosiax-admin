import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRaRecord } from 'app/shared/model/ra-record.model';

type EntityResponseType = HttpResponse<IRaRecord>;
type EntityArrayResponseType = HttpResponse<IRaRecord[]>;

@Injectable({ providedIn: 'root' })
export class RaRecordService {
  public resourceUrl = SERVER_API_URL + 'api/ra-records';

  constructor(protected http: HttpClient) {}

  create(raRecord: IRaRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(raRecord);
    return this.http
      .post<IRaRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(raRecord: IRaRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(raRecord);
    return this.http
      .put<IRaRecord>(this.resourceUrl, copy, { observe: 'response' })
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

  protected convertDateFromClient(raRecord: IRaRecord): IRaRecord {
    const copy: IRaRecord = Object.assign({}, raRecord, {
      signatureDate: raRecord.signatureDate && raRecord.signatureDate.isValid() ? raRecord.signatureDate.toJSON() : undefined,
      validationDate: raRecord.validationDate && raRecord.validationDate.isValid() ? raRecord.validationDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.signatureDate = res.body.signatureDate ? moment(res.body.signatureDate) : undefined;
      res.body.validationDate = res.body.validationDate ? moment(res.body.validationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((raRecord: IRaRecord) => {
        raRecord.signatureDate = raRecord.signatureDate ? moment(raRecord.signatureDate) : undefined;
        raRecord.validationDate = raRecord.validationDate ? moment(raRecord.validationDate) : undefined;
      });
    }
    return res;
  }
}
