import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVoucher, Voucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';
import { IRaRecord } from 'app/entities/ra-record/ra-record.model';
import { RaRecordService } from 'app/entities/ra-record/service/ra-record.service';

@Component({
  selector: 'jhi-voucher-update',
  templateUrl: './voucher-update.component.html',
})
export class VoucherUpdateComponent implements OnInit {
  isSaving = false;

  raRecordsSharedCollection: IRaRecord[] = [];

  editForm = this.fb.group({
    id: [],
    fileName: [null, [Validators.required]],
    key: [null, [Validators.required]],
    raRecord: [],
  });

  constructor(
    protected voucherService: VoucherService,
    protected raRecordService: RaRecordService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.updateForm(voucher);

      this.loadRelationshipsOptions();
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

  trackRaRecordById(index: number, item: IRaRecord): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(voucher: IVoucher): void {
    this.editForm.patchValue({
      id: voucher.id,
      fileName: voucher.fileName,
      key: voucher.key,
      raRecord: voucher.raRecord,
    });

    this.raRecordsSharedCollection = this.raRecordService.addRaRecordToCollectionIfMissing(
      this.raRecordsSharedCollection,
      voucher.raRecord
    );
  }

  protected loadRelationshipsOptions(): void {
    this.raRecordService
      .query()
      .pipe(map((res: HttpResponse<IRaRecord[]>) => res.body ?? []))
      .pipe(
        map((raRecords: IRaRecord[]) =>
          this.raRecordService.addRaRecordToCollectionIfMissing(raRecords, this.editForm.get('raRecord')!.value)
        )
      )
      .subscribe((raRecords: IRaRecord[]) => (this.raRecordsSharedCollection = raRecords));
  }

  protected createFromForm(): IVoucher {
    return {
      ...new Voucher(),
      id: this.editForm.get(['id'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      key: this.editForm.get(['key'])!.value,
      raRecord: this.editForm.get(['raRecord'])!.value,
    };
  }
}
