import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRaRecord, RaRecord } from 'app/shared/model/ra-record.model';
import { RaRecordService } from './ra-record.service';

@Component({
  selector: 'jhi-ra-record-update',
  templateUrl: './ra-record-update.component.html'
})
export class RaRecordUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]],
    idUser: [null, [Validators.required]],
    identifier: [null, [Validators.required]],
    certO: [null, [Validators.required]],
    commonName: [null, [Validators.required]],
    locality: [null, [Validators.required]],
    country: [null, [Validators.required]],
    lastname: [null, [Validators.required]],
    firstname: [null, [Validators.required]],
    email: [null, [Validators.required]],
    phone: [null, [Validators.required]]
  });

  constructor(protected raRecordService: RaRecordService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ raRecord }) => {
      this.updateForm(raRecord);
    });
  }

  updateForm(raRecord: IRaRecord): void {
    this.editForm.patchValue({
      id: raRecord.id,
      status: raRecord.status,
      idUser: raRecord.idUser,
      identifier: raRecord.identifier,
      certO: raRecord.certO,
      commonName: raRecord.commonName,
      locality: raRecord.locality,
      country: raRecord.country,
      lastname: raRecord.lastname,
      firstname: raRecord.firstname,
      email: raRecord.email,
      phone: raRecord.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const raRecord = this.createFromForm();
    if (raRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.raRecordService.update(raRecord));
    } else {
      this.subscribeToSaveResponse(this.raRecordService.create(raRecord));
    }
  }

  private createFromForm(): IRaRecord {
    return {
      ...new RaRecord(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      certO: this.editForm.get(['certO'])!.value,
      commonName: this.editForm.get(['commonName'])!.value,
      locality: this.editForm.get(['locality'])!.value,
      country: this.editForm.get(['country'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRaRecord>>): void {
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
}
