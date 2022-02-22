import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdditionalKeys } from '../additional-keys.model';
import { AdditionalKeysService } from '../service/additional-keys.service';
import { AdditionalKeysDeleteDialogComponent } from '../delete/additional-keys-delete-dialog.component';

@Component({
  selector: 'jhi-additional-keys',
  templateUrl: './additional-keys.component.html',
})
export class AdditionalKeysComponent implements OnInit {
  additionalKeys?: IAdditionalKeys[];
  isLoading = false;

  constructor(protected additionalKeysService: AdditionalKeysService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.additionalKeysService.query().subscribe({
      next: (res: HttpResponse<IAdditionalKeys[]>) => {
        this.isLoading = false;
        this.additionalKeys = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAdditionalKeys): number {
    return item.id!;
  }

  delete(additionalKeys: IAdditionalKeys): void {
    const modalRef = this.modalService.open(AdditionalKeysDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.additionalKeys = additionalKeys;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
