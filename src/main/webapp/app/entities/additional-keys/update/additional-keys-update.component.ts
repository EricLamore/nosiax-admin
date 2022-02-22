import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdditionalKeys, AdditionalKeys } from '../additional-keys.model';
import { AdditionalKeysService } from '../service/additional-keys.service';
import { IRaRecord } from 'app/entities/ra-record/ra-record.model';
import { RaRecordService } from 'app/entities/ra-record/service/ra-record.service';

@Component({
  selector: 'jhi-additional-keys-update',
  templateUrl: './additional-keys-update.component.html',
})
export class AdditionalKeysUpdateComponent implements OnInit {
  isSaving = false;

  raRecordsSharedCollection: IRaRecord[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    value: [null, [Validators.required]],
    raRecord: [],
  });

  constructor(
    protected additionalKeysService: AdditionalKeysService,
    protected raRecordService: RaRecordService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ additionalKeys }) => {
      this.updateForm(additionalKeys);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const additionalKeys = this.createFromForm();
    if (additionalKeys.id !== undefined) {
      this.subscribeToSaveResponse(this.additionalKeysService.update(additionalKeys));
    } else {
      this.subscribeToSaveResponse(this.additionalKeysService.create(additionalKeys));
    }
  }

  trackRaRecordById(index: number, item: IRaRecord): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdditionalKeys>>): void {
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

  protected updateForm(additionalKeys: IAdditionalKeys): void {
    this.editForm.patchValue({
      id: additionalKeys.id,
      key: additionalKeys.key,
      value: additionalKeys.value,
      raRecord: additionalKeys.raRecord,
    });

    this.raRecordsSharedCollection = this.raRecordService.addRaRecordToCollectionIfMissing(
      this.raRecordsSharedCollection,
      additionalKeys.raRecord
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

  protected createFromForm(): IAdditionalKeys {
    return {
      ...new AdditionalKeys(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      value: this.editForm.get(['value'])!.value,
      raRecord: this.editForm.get(['raRecord'])!.value,
    };
  }
}
