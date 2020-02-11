import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClosingRequest } from 'app/shared/model/closing-request.model';

type EntityResponseType = HttpResponse<IClosingRequest>;
type EntityArrayResponseType = HttpResponse<IClosingRequest[]>;

@Injectable({ providedIn: 'root' })
export class ClosingRequestService {
  public resourceUrl = SERVER_API_URL + 'api/closing-requests';

  constructor(protected http: HttpClient) {}

  create(closingRequest: IClosingRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(closingRequest);
    return this.http
      .post<IClosingRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(closingRequest: IClosingRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(closingRequest);
    return this.http
      .put<IClosingRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClosingRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClosingRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(closingRequest: IClosingRequest): IClosingRequest {
    const copy: IClosingRequest = Object.assign({}, closingRequest, {
      closingDate: closingRequest.closingDate && closingRequest.closingDate.isValid() ? closingRequest.closingDate.toJSON() : undefined,
      revokeCertificateDate:
        closingRequest.revokeCertificateDate && closingRequest.revokeCertificateDate.isValid()
          ? closingRequest.revokeCertificateDate.toJSON()
          : undefined,
      linkEsignDate:
        closingRequest.linkEsignDate && closingRequest.linkEsignDate.isValid() ? closingRequest.linkEsignDate.toJSON() : undefined,
      anonymizedDate:
        closingRequest.anonymizedDate && closingRequest.anonymizedDate.isValid() ? closingRequest.anonymizedDate.toJSON() : undefined,
      createBillDate:
        closingRequest.createBillDate && closingRequest.createBillDate.isValid() ? closingRequest.createBillDate.toJSON() : undefined,
      sendBillDate: closingRequest.sendBillDate && closingRequest.sendBillDate.isValid() ? closingRequest.sendBillDate.toJSON() : undefined,
      terminateDate:
        closingRequest.terminateDate && closingRequest.terminateDate.isValid() ? closingRequest.terminateDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.closingDate = res.body.closingDate ? moment(res.body.closingDate) : undefined;
      res.body.revokeCertificateDate = res.body.revokeCertificateDate ? moment(res.body.revokeCertificateDate) : undefined;
      res.body.linkEsignDate = res.body.linkEsignDate ? moment(res.body.linkEsignDate) : undefined;
      res.body.anonymizedDate = res.body.anonymizedDate ? moment(res.body.anonymizedDate) : undefined;
      res.body.createBillDate = res.body.createBillDate ? moment(res.body.createBillDate) : undefined;
      res.body.sendBillDate = res.body.sendBillDate ? moment(res.body.sendBillDate) : undefined;
      res.body.terminateDate = res.body.terminateDate ? moment(res.body.terminateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((closingRequest: IClosingRequest) => {
        closingRequest.closingDate = closingRequest.closingDate ? moment(closingRequest.closingDate) : undefined;
        closingRequest.revokeCertificateDate = closingRequest.revokeCertificateDate
          ? moment(closingRequest.revokeCertificateDate)
          : undefined;
        closingRequest.linkEsignDate = closingRequest.linkEsignDate ? moment(closingRequest.linkEsignDate) : undefined;
        closingRequest.anonymizedDate = closingRequest.anonymizedDate ? moment(closingRequest.anonymizedDate) : undefined;
        closingRequest.createBillDate = closingRequest.createBillDate ? moment(closingRequest.createBillDate) : undefined;
        closingRequest.sendBillDate = closingRequest.sendBillDate ? moment(closingRequest.sendBillDate) : undefined;
        closingRequest.terminateDate = closingRequest.terminateDate ? moment(closingRequest.terminateDate) : undefined;
      });
    }
    return res;
  }
}
