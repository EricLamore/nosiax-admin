import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdditionalKeys } from 'app/shared/model/additional-keys.model';

type EntityResponseType = HttpResponse<IAdditionalKeys>;
type EntityArrayResponseType = HttpResponse<IAdditionalKeys[]>;

@Injectable({ providedIn: 'root' })
export class AdditionalKeysService {
  public resourceUrl = SERVER_API_URL + 'api/additional-keys';

  constructor(protected http: HttpClient) {}

  create(additionalKeys: IAdditionalKeys): Observable<EntityResponseType> {
    return this.http.post<IAdditionalKeys>(this.resourceUrl, additionalKeys, { observe: 'response' });
  }

  update(additionalKeys: IAdditionalKeys): Observable<EntityResponseType> {
    return this.http.put<IAdditionalKeys>(this.resourceUrl, additionalKeys, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdditionalKeys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdditionalKeys[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
