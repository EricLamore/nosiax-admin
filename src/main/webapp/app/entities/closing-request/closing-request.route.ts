import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClosingRequest, ClosingRequest } from 'app/shared/model/closing-request.model';
import { ClosingRequestService } from './closing-request.service';
import { ClosingRequestComponent } from './closing-request.component';
import { ClosingRequestDetailComponent } from './closing-request-detail.component';
import { ClosingRequestUpdateComponent } from './closing-request-update.component';

@Injectable({ providedIn: 'root' })
export class ClosingRequestResolve implements Resolve<IClosingRequest> {
  constructor(private service: ClosingRequestService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClosingRequest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((closingRequest: HttpResponse<ClosingRequest>) => {
          if (closingRequest.body) {
            return of(closingRequest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClosingRequest());
  }
}

export const closingRequestRoute: Routes = [
  {
    path: '',
    component: ClosingRequestComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ClosingRequests'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClosingRequestDetailComponent,
    resolve: {
      closingRequest: ClosingRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClosingRequests'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClosingRequestUpdateComponent,
    resolve: {
      closingRequest: ClosingRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClosingRequests'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClosingRequestUpdateComponent,
    resolve: {
      closingRequest: ClosingRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClosingRequests'
    },
    canActivate: [UserRouteAccessService]
  }
];
