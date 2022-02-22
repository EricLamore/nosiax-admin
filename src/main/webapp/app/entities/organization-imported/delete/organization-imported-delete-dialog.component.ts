import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationImported } from '../organization-imported.model';
import { OrganizationImportedService } from '../service/organization-imported.service';

@Component({
  templateUrl: './organization-imported-delete-dialog.component.html',
})
export class OrganizationImportedDeleteDialogComponent {
  organizationImported?: IOrganizationImported;

  constructor(protected organizationImportedService: OrganizationImportedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationImportedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
