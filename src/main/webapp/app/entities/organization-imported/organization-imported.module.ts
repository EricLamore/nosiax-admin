import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizationImportedComponent } from './list/organization-imported.component';
import { OrganizationImportedDetailComponent } from './detail/organization-imported-detail.component';
import { OrganizationImportedUpdateComponent } from './update/organization-imported-update.component';
import { OrganizationImportedDeleteDialogComponent } from './delete/organization-imported-delete-dialog.component';
import { OrganizationImportedRoutingModule } from './route/organization-imported-routing.module';

@NgModule({
  imports: [SharedModule, OrganizationImportedRoutingModule],
  declarations: [
    OrganizationImportedComponent,
    OrganizationImportedDetailComponent,
    OrganizationImportedUpdateComponent,
    OrganizationImportedDeleteDialogComponent,
  ],
  entryComponents: [OrganizationImportedDeleteDialogComponent],
})
export class OrganizationImportedModule {}
