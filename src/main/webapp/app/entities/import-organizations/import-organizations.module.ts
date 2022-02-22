import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ImportOrganizationsComponent } from './list/import-organizations.component';
import { ImportOrganizationsDetailComponent } from './detail/import-organizations-detail.component';
import { ImportOrganizationsUpdateComponent } from './update/import-organizations-update.component';
import { ImportOrganizationsDeleteDialogComponent } from './delete/import-organizations-delete-dialog.component';
import { ImportOrganizationsRoutingModule } from './route/import-organizations-routing.module';

@NgModule({
  imports: [SharedModule, ImportOrganizationsRoutingModule],
  declarations: [
    ImportOrganizationsComponent,
    ImportOrganizationsDetailComponent,
    ImportOrganizationsUpdateComponent,
    ImportOrganizationsDeleteDialogComponent,
  ],
  entryComponents: [ImportOrganizationsDeleteDialogComponent],
})
export class ImportOrganizationsModule {}
