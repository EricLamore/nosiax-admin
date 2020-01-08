import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IVoucher, Voucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';
import { IRaRecord } from 'app/shared/model/ra-record.model';
import { RaRecordService } from 'app/entities/ra-record/ra-record.service';

@Component({
  selector: 'jhi-voucher-update',
  templateUrl: './voucher-update.component.html'
})
export class VoucherUpdateComponent implements OnInit {
  isSaving = false;

  rarecords: IRaRecord[] = [];

  editForm = this.fb.group({
    id: [],
    fileName: [null, [Validators.required]],
    key: [null, [Validators.required]],
    raRecord: []
  });

  constructor(
    protected voucherService: VoucherService,
    protected raRecordService: RaRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.updateForm(voucher);

      this.raRecordService
        .query()
        .pipe(
          map((res: HttpResponse<IRaRecord[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IRaRecord[]) => (this.rarecords = resBody));
    });
  }

  updateForm(voucher: IVoucher): void {
    this.editForm.patchValue({
      id: voucher.id,
      fileName: voucher.fileName,
      key: voucher.key,
      raRecord: voucher.raRecord
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucher = this.createFromForm();
    if (voucher.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherService.update(voucher));
    } else {
      this.subscribeToSaveResponse(this.voucherService.create(voucher));
    }
  }

  private createFromForm(): IVoucher {
    return {
      ...new Voucher(),
      id: this.editForm.get(['id'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      key: this.editForm.get(['key'])!.value,
      raRecord: this.editForm.get(['raRecord'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IRaRecord): any {
    return item.id;
  }
}
