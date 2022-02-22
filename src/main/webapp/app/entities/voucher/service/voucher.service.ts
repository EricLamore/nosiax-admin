import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucher, getVoucherIdentifier } from '../voucher.model';

export type EntityResponseType = HttpResponse<IVoucher>;
export type EntityArrayResponseType = HttpResponse<IVoucher[]>;

@Injectable({ providedIn: 'root' })
export class VoucherService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vouchers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(voucher: IVoucher): Observable<EntityResponseType> {
    return this.http.post<IVoucher>(this.resourceUrl, voucher, { observe: 'response' });
  }

  update(voucher: IVoucher): Observable<EntityResponseType> {
    return this.http.put<IVoucher>(`${this.resourceUrl}/${getVoucherIdentifier(voucher) as number}`, voucher, { observe: 'response' });
  }

  partialUpdate(voucher: IVoucher): Observable<EntityResponseType> {
    return this.http.patch<IVoucher>(`${this.resourceUrl}/${getVoucherIdentifier(voucher) as number}`, voucher, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoucher[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVoucherToCollectionIfMissing(voucherCollection: IVoucher[], ...vouchersToCheck: (IVoucher | null | undefined)[]): IVoucher[] {
    const vouchers: IVoucher[] = vouchersToCheck.filter(isPresent);
    if (vouchers.length > 0) {
      const voucherCollectionIdentifiers = voucherCollection.map(voucherItem => getVoucherIdentifier(voucherItem)!);
      const vouchersToAdd = vouchers.filter(voucherItem => {
        const voucherIdentifier = getVoucherIdentifier(voucherItem);
        if (voucherIdentifier == null || voucherCollectionIdentifiers.includes(voucherIdentifier)) {
          return false;
        }
        voucherCollectionIdentifiers.push(voucherIdentifier);
        return true;
      });
      return [...vouchersToAdd, ...voucherCollection];
    }
    return voucherCollection;
  }
}
