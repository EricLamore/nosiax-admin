import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdditionalKeys, AdditionalKeys } from '../additional-keys.model';
import { AdditionalKeysService } from '../service/additional-keys.service';

@Injectable({ providedIn: 'root' })
export class AdditionalKeysRoutingResolveService implements Resolve<IAdditionalKeys> {
  constructor(protected service: AdditionalKeysService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdditionalKeys> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((additionalKeys: HttpResponse<AdditionalKeys>) => {
          if (additionalKeys.body) {
            return of(additionalKeys.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdditionalKeys());
  }
}
