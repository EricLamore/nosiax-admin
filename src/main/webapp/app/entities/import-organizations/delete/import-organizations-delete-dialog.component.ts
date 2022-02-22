import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IImportOrganizations } from '../import-organizations.model';
import { ImportOrganizationsService } from '../service/import-organizations.service';

@Component({
  templateUrl: './import-organizations-delete-dialog.component.html',
})
export class ImportOrganizationsDeleteDialogComponent {
  importOrganizations?: IImportOrganizations;

  constructor(protected importOrganizationsService: ImportOrganizationsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.importOrganizationsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
