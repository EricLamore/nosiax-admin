import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

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
    zipCode: [null, [Validators.required]],
    locality: [null, [Validators.required]],
    country: [null, [Validators.required]],
    lastname: [null, [Validators.required]],
    firstname: [null, [Validators.required]],
    email: [null, [Validators.required]],
    phone: [null, [Validators.required]],
    url: [],
    idTransaction: [],
    transactionStatus: [],
    profilCpm: [],
    reaso: [],
    signatureDate: [],
    validationDate: []
  });

  constructor(protected raRecordService: RaRecordService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ raRecord }) => {
      if (!raRecord.id) {
        const today = moment().startOf('day');
        raRecord.signatureDate = today;
        raRecord.validationDate = today;
      }

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
      zipCode: raRecord.zipCode,
      locality: raRecord.locality,
      country: raRecord.country,
      lastname: raRecord.lastname,
      firstname: raRecord.firstname,
      email: raRecord.email,
      phone: raRecord.phone,
      url: raRecord.url,
      idTransaction: raRecord.idTransaction,
      transactionStatus: raRecord.transactionStatus,
      profilCpm: raRecord.profilCpm,
      reaso: raRecord.reaso,
      signatureDate: raRecord.signatureDate ? raRecord.signatureDate.format(DATE_TIME_FORMAT) : null,
      validationDate: raRecord.validationDate ? raRecord.validationDate.format(DATE_TIME_FORMAT) : null
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
      zipCode: this.editForm.get(['zipCode'])!.value,
      locality: this.editForm.get(['locality'])!.value,
      country: this.editForm.get(['country'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      url: this.editForm.get(['url'])!.value,
      idTransaction: this.editForm.get(['idTransaction'])!.value,
      transactionStatus: this.editForm.get(['transactionStatus'])!.value,
      profilCpm: this.editForm.get(['profilCpm'])!.value,
      reaso: this.editForm.get(['reaso'])!.value,
      signatureDate: this.editForm.get(['signatureDate'])!.value
        ? moment(this.editForm.get(['signatureDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      validationDate: this.editForm.get(['validationDate'])!.value
        ? moment(this.editForm.get(['validationDate'])!.value, DATE_TIME_FORMAT)
        : undefined
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
