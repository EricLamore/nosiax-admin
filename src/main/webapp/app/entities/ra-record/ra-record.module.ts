import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RaRecordComponent } from './list/ra-record.component';
import { RaRecordDetailComponent } from './detail/ra-record-detail.component';
import { RaRecordUpdateComponent } from './update/ra-record-update.component';
import { RaRecordDeleteDialogComponent } from './delete/ra-record-delete-dialog.component';
import { RaRecordRoutingModule } from './route/ra-record-routing.module';

@NgModule({
  imports: [SharedModule, RaRecordRoutingModule],
  declarations: [RaRecordComponent, RaRecordDetailComponent, RaRecordUpdateComponent, RaRecordDeleteDialogComponent],
  entryComponents: [RaRecordDeleteDialogComponent],
})
export class RaRecordModule {}
