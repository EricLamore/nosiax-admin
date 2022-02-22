import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRaRecord, RaRecord } from '../ra-record.model';
import { RaRecordService } from '../service/ra-record.service';

@Injectable({ providedIn: 'root' })
export class RaRecordRoutingResolveService implements Resolve<IRaRecord> {
  constructor(protected service: RaRecordService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRaRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((raRecord: HttpResponse<RaRecord>) => {
          if (raRecord.body) {
            return of(raRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RaRecord());
  }
}
