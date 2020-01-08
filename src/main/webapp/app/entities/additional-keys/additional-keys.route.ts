import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdditionalKeys, AdditionalKeys } from 'app/shared/model/additional-keys.model';
import { AdditionalKeysService } from './additional-keys.service';
import { AdditionalKeysComponent } from './additional-keys.component';
import { AdditionalKeysDetailComponent } from './additional-keys-detail.component';
import { AdditionalKeysUpdateComponent } from './additional-keys-update.component';

@Injectable({ providedIn: 'root' })
export class AdditionalKeysResolve implements Resolve<IAdditionalKeys> {
  constructor(private service: AdditionalKeysService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdditionalKeys> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((additionalKeys: HttpResponse<AdditionalKeys>) => {
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

export const additionalKeysRoute: Routes = [
  {
    path: '',
    component: AdditionalKeysComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AdditionalKeys'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdditionalKeysDetailComponent,
    resolve: {
      additionalKeys: AdditionalKeysResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AdditionalKeys'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdditionalKeysUpdateComponent,
    resolve: {
      additionalKeys: AdditionalKeysResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AdditionalKeys'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdditionalKeysUpdateComponent,
    resolve: {
      additionalKeys: AdditionalKeysResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AdditionalKeys'
    },
    canActivate: [UserRouteAccessService]
  }
];
