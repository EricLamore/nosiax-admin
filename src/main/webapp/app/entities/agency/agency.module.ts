import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminNosiaxSharedModule } from 'app/shared/shared.module';
import { AgencyComponent } from './agency.component';
import { AgencyDetailComponent } from './agency-detail.component';
import { AgencyUpdateComponent } from './agency-update.component';
import { AgencyDeleteDialogComponent } from './agency-delete-dialog.component';
import { agencyRoute } from './agency.route';

@NgModule({
  imports: [AdminNosiaxSharedModule, RouterModule.forChild(agencyRoute)],
  declarations: [AgencyComponent, AgencyDetailComponent, AgencyUpdateComponent, AgencyDeleteDialogComponent],
  entryComponents: [AgencyDeleteDialogComponent]
})
export class AdminNosiaxAgencyModule {}
