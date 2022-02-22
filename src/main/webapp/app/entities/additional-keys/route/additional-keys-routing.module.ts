import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdditionalKeysComponent } from '../list/additional-keys.component';
import { AdditionalKeysDetailComponent } from '../detail/additional-keys-detail.component';
import { AdditionalKeysUpdateComponent } from '../update/additional-keys-update.component';
import { AdditionalKeysRoutingResolveService } from './additional-keys-routing-resolve.service';

const additionalKeysRoute: Routes = [
  {
    path: '',
    component: AdditionalKeysComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdditionalKeysDetailComponent,
    resolve: {
      additionalKeys: AdditionalKeysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdditionalKeysUpdateComponent,
    resolve: {
      additionalKeys: AdditionalKeysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdditionalKeysUpdateComponent,
    resolve: {
      additionalKeys: AdditionalKeysRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(additionalKeysRoute)],
  exports: [RouterModule],
})
export class AdditionalKeysRoutingModule {}
