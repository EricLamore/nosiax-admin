import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminNosiaxSharedModule } from 'app/shared/shared.module';
import { RaRecordComponent } from './ra-record.component';
import { RaRecordDetailComponent } from './ra-record-detail.component';
import { RaRecordUpdateComponent } from './ra-record-update.component';
import { RaRecordDeleteDialogComponent } from './ra-record-delete-dialog.component';
import { raRecordRoute } from './ra-record.route';

@NgModule({
  imports: [AdminNosiaxSharedModule, RouterModule.forChild(raRecordRoute)],
  declarations: [RaRecordComponent, RaRecordDetailComponent, RaRecordUpdateComponent, RaRecordDeleteDialogComponent],
  entryComponents: [RaRecordDeleteDialogComponent]
})
export class AdminNosiaxRaRecordModule {}
