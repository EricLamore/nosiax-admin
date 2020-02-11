import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminNosiaxSharedModule } from 'app/shared/shared.module';
import { ClosingRequestComponent } from './closing-request.component';
import { ClosingRequestDetailComponent } from './closing-request-detail.component';
import { ClosingRequestUpdateComponent } from './closing-request-update.component';
import { ClosingRequestDeleteDialogComponent } from './closing-request-delete-dialog.component';
import { closingRequestRoute } from './closing-request.route';

@NgModule({
  imports: [AdminNosiaxSharedModule, RouterModule.forChild(closingRequestRoute)],
  declarations: [
    ClosingRequestComponent,
    ClosingRequestDetailComponent,
    ClosingRequestUpdateComponent,
    ClosingRequestDeleteDialogComponent
  ],
  entryComponents: [ClosingRequestDeleteDialogComponent]
})
export class AdminNosiaxClosingRequestModule {}
