import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IRaRecord>(this.resourceUrl, raRecord, { observe: 'response' });
  }

  update(raRecord: IRaRecord): Observable<EntityResponseType> {
    return this.http.put<IRaRecord>(this.resourceUrl, raRecord, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRaRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRaRecord[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
