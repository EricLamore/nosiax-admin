import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';
import { VoucherDeleteDialogComponent } from './voucher-delete-dialog.component';

@Component({
  selector: 'jhi-voucher',
  templateUrl: './voucher.component.html'
})
export class VoucherComponent implements OnInit, OnDestroy {
  vouchers?: IVoucher[];
  eventSubscriber?: Subscription;

  constructor(protected voucherService: VoucherService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.voucherService.query().subscribe((res: HttpResponse<IVoucher[]>) => {
      this.vouchers = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVouchers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVoucher): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVouchers(): void {
    this.eventSubscriber = this.eventManager.subscribe('voucherListModification', () => this.loadAll());
  }

  delete(voucher: IVoucher): void {
    const modalRef = this.modalService.open(VoucherDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voucher = voucher;
  }
}
