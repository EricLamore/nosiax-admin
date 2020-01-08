import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRaRecord } from 'app/shared/model/ra-record.model';
import { RaRecordService } from './ra-record.service';

@Component({
  templateUrl: './ra-record-delete-dialog.component.html'
})
export class RaRecordDeleteDialogComponent {
  raRecord?: IRaRecord;

  constructor(protected raRecordService: RaRecordService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.raRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('raRecordListModification');
      this.activeModal.close();
    });
  }
}
