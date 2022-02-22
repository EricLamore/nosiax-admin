import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationImportedComponent } from '../list/organization-imported.component';
import { OrganizationImportedDetailComponent } from '../detail/organization-imported-detail.component';
import { OrganizationImportedUpdateComponent } from '../update/organization-imported-update.component';
import { OrganizationImportedRoutingResolveService } from './organization-imported-routing-resolve.service';

const organizationImportedRoute: Routes = [
  {
    path: '',
    component: OrganizationImportedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationImportedDetailComponent,
    resolve: {
      organizationImported: OrganizationImportedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationImportedUpdateComponent,
    resolve: {
      organizationImported: OrganizationImportedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationImportedUpdateComponent,
    resolve: {
      organizationImported: OrganizationImportedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationImportedRoute)],
  exports: [RouterModule],
})
export class OrganizationImportedRoutingModule {}
