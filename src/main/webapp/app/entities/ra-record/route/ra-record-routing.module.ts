import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RaRecordComponent } from '../list/ra-record.component';
import { RaRecordDetailComponent } from '../detail/ra-record-detail.component';
import { RaRecordUpdateComponent } from '../update/ra-record-update.component';
import { RaRecordRoutingResolveService } from './ra-record-routing-resolve.service';

const raRecordRoute: Routes = [
  {
    path: '',
    component: RaRecordComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RaRecordDetailComponent,
    resolve: {
      raRecord: RaRecordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RaRecordUpdateComponent,
    resolve: {
      raRecord: RaRecordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RaRecordUpdateComponent,
    resolve: {
      raRecord: RaRecordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(raRecordRoute)],
  exports: [RouterModule],
})
export class RaRecordRoutingModule {}
