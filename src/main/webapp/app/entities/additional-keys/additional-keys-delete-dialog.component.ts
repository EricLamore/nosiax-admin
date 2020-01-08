import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdditionalKeys } from 'app/shared/model/additional-keys.model';
import { AdditionalKeysService } from './additional-keys.service';

@Component({
  templateUrl: './additional-keys-delete-dialog.component.html'
})
export class AdditionalKeysDeleteDialogComponent {
  additionalKeys?: IAdditionalKeys;

  constructor(
    protected additionalKeysService: AdditionalKeysService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.additionalKeysService.delete(id).subscribe(() => {
      this.eventManager.broadcast('additionalKeysListModification');
      this.activeModal.close();
    });
  }
}
