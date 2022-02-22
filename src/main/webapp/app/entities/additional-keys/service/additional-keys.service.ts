import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdditionalKeys, getAdditionalKeysIdentifier } from '../additional-keys.model';

export type EntityResponseType = HttpResponse<IAdditionalKeys>;
export type EntityArrayResponseType = HttpResponse<IAdditionalKeys[]>;

@Injectable({ providedIn: 'root' })
export class AdditionalKeysService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/additional-keys');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(additionalKeys: IAdditionalKeys): Observable<EntityResponseType> {
    return this.http.post<IAdditionalKeys>(this.resourceUrl, additionalKeys, { observe: 'response' });
  }

  update(additionalKeys: IAdditionalKeys): Observable<EntityResponseType> {
    return this.http.put<IAdditionalKeys>(`${this.resourceUrl}/${getAdditionalKeysIdentifier(additionalKeys) as number}`, additionalKeys, {
      observe: 'response',
    });
  }

  partialUpdate(additionalKeys: IAdditionalKeys): Observable<EntityResponseType> {
    return this.http.patch<IAdditionalKeys>(
      `${this.resourceUrl}/${getAdditionalKeysIdentifier(additionalKeys) as number}`,
      additionalKeys,
      { observe: 'response' }
    );
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

  addAdditionalKeysToCollectionIfMissing(
    additionalKeysCollection: IAdditionalKeys[],
    ...additionalKeysToCheck: (IAdditionalKeys | null | undefined)[]
  ): IAdditionalKeys[] {
    const additionalKeys: IAdditionalKeys[] = additionalKeysToCheck.filter(isPresent);
    if (additionalKeys.length > 0) {
      const additionalKeysCollectionIdentifiers = additionalKeysCollection.map(
        additionalKeysItem => getAdditionalKeysIdentifier(additionalKeysItem)!
      );
      const additionalKeysToAdd = additionalKeys.filter(additionalKeysItem => {
        const additionalKeysIdentifier = getAdditionalKeysIdentifier(additionalKeysItem);
        if (additionalKeysIdentifier == null || additionalKeysCollectionIdentifiers.includes(additionalKeysIdentifier)) {
          return false;
        }
        additionalKeysCollectionIdentifiers.push(additionalKeysIdentifier);
        return true;
      });
      return [...additionalKeysToAdd, ...additionalKeysCollection];
    }
    return additionalKeysCollection;
  }
}
