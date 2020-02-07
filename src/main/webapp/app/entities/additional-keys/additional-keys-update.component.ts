import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdditionalKeys, AdditionalKeys } from 'app/shared/model/additional-keys.model';
import { AdditionalKeysService } from './additional-keys.service';
import { IRaRecord } from 'app/shared/model/ra-record.model';
import { RaRecordService } from 'app/entities/ra-record/ra-record.service';

@Component({
  selector: 'jhi-additional-keys-update',
  templateUrl: './additional-keys-update.component.html'
})
export class AdditionalKeysUpdateComponent implements OnInit {
  isSaving = false;
  rarecords: IRaRecord[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    value: [null, [Validators.required]],
    raRecord: []
  });

  constructor(
    protected additionalKeysService: AdditionalKeysService,
    protected raRecordService: RaRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ additionalKeys }) => {
      this.updateForm(additionalKeys);

      this.raRecordService.query().subscribe((res: HttpResponse<IRaRecord[]>) => (this.rarecords = res.body || []));
    });
  }

  updateForm(additionalKeys: IAdditionalKeys): void {
    this.editForm.patchValue({
      id: additionalKeys.id,
      key: additionalKeys.key,
      value: additionalKeys.value,
      raRecord: additionalKeys.raRecord
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

  private createFromForm(): IAdditionalKeys {
    return {
      ...new AdditionalKeys(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      value: this.editForm.get(['value'])!.value,
      raRecord: this.editForm.get(['raRecord'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdditionalKeys>>): void {
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
