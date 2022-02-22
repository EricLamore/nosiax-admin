import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRaRecord } from '../ra-record.model';
import { RaRecordService } from '../service/ra-record.service';

@Component({
  templateUrl: './ra-record-delete-dialog.component.html',
})
export class RaRecordDeleteDialogComponent {
  raRecord?: IRaRecord;

  constructor(protected raRecordService: RaRecordService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.raRecordService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
