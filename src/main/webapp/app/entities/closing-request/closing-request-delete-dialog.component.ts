import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClosingRequest } from 'app/shared/model/closing-request.model';
import { ClosingRequestService } from './closing-request.service';

@Component({
  templateUrl: './closing-request-delete-dialog.component.html'
})
export class ClosingRequestDeleteDialogComponent {
  closingRequest?: IClosingRequest;

  constructor(
    protected closingRequestService: ClosingRequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.closingRequestService.delete(id).subscribe(() => {
      this.eventManager.broadcast('closingRequestListModification');
      this.activeModal.close();
    });
  }
}
