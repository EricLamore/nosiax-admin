import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ImportOrganizationsComponent } from '../list/import-organizations.component';
import { ImportOrganizationsDetailComponent } from '../detail/import-organizations-detail.component';
import { ImportOrganizationsUpdateComponent } from '../update/import-organizations-update.component';
import { ImportOrganizationsRoutingResolveService } from './import-organizations-routing-resolve.service';

const importOrganizationsRoute: Routes = [
  {
    path: '',
    component: ImportOrganizationsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImportOrganizationsDetailComponent,
    resolve: {
      importOrganizations: ImportOrganizationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImportOrganizationsUpdateComponent,
    resolve: {
      importOrganizations: ImportOrganizationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImportOrganizationsUpdateComponent,
    resolve: {
      importOrganizations: ImportOrganizationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(importOrganizationsRoute)],
  exports: [RouterModule],
})
export class ImportOrganizationsRoutingModule {}
