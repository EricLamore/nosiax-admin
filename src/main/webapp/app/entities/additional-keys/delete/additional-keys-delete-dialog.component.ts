import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdditionalKeys } from '../additional-keys.model';
import { AdditionalKeysService } from '../service/additional-keys.service';

@Component({
  templateUrl: './additional-keys-delete-dialog.component.html',
})
export class AdditionalKeysDeleteDialogComponent {
  additionalKeys?: IAdditionalKeys;

  constructor(protected additionalKeysService: AdditionalKeysService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.additionalKeysService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
