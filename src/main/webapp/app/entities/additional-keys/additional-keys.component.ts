import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdditionalKeys } from 'app/shared/model/additional-keys.model';
import { AdditionalKeysService } from './additional-keys.service';
import { AdditionalKeysDeleteDialogComponent } from './additional-keys-delete-dialog.component';

@Component({
  selector: 'jhi-additional-keys',
  templateUrl: './additional-keys.component.html'
})
export class AdditionalKeysComponent implements OnInit, OnDestroy {
  additionalKeys?: IAdditionalKeys[];
  eventSubscriber?: Subscription;

  constructor(
    protected additionalKeysService: AdditionalKeysService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.additionalKeysService.query().subscribe((res: HttpResponse<IAdditionalKeys[]>) => {
      this.additionalKeys = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAdditionalKeys();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdditionalKeys): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAdditionalKeys(): void {
    this.eventSubscriber = this.eventManager.subscribe('additionalKeysListModification', () => this.loadAll());
  }

  delete(additionalKeys: IAdditionalKeys): void {
    const modalRef = this.modalService.open(AdditionalKeysDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.additionalKeys = additionalKeys;
  }
}
