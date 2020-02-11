import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IClosingRequest, ClosingRequest } from 'app/shared/model/closing-request.model';
import { ClosingRequestService } from './closing-request.service';
import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from 'app/entities/agency/agency.service';

@Component({
  selector: 'jhi-closing-request-update',
  templateUrl: './closing-request-update.component.html'
})
export class ClosingRequestUpdateComponent implements OnInit {
  isSaving = false;
  idxagencies: IAgency[] = [];

  editForm = this.fb.group({
    id: [],
    idxAgency: [],
    closingStatus: [],
    closingDate: [],
    revokeCertificate: [],
    revokeCertificateDate: [],
    linkEsign: [],
    linkEsignDate: [],
    anonymized: [],
    anonymizedDate: [],
    createBill: [],
    createBillDate: [],
    sendBill: [],
    sendBillDate: [],
    terminate: [],
    terminateDate: [],
    idxAgency: []
  });

  constructor(
    protected closingRequestService: ClosingRequestService,
    protected agencyService: AgencyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ closingRequest }) => {
      if (!closingRequest.id) {
        const today = moment().startOf('day');
        closingRequest.closingDate = today;
        closingRequest.revokeCertificateDate = today;
        closingRequest.linkEsignDate = today;
        closingRequest.anonymizedDate = today;
        closingRequest.createBillDate = today;
        closingRequest.sendBillDate = today;
        closingRequest.terminateDate = today;
      }

      this.updateForm(closingRequest);

      this.agencyService
        .query({ filter: 'closingrequest-is-null' })
        .pipe(
          map((res: HttpResponse<IAgency[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAgency[]) => {
          if (!closingRequest.idxAgency || !closingRequest.idxAgency.id) {
            this.idxagencies = resBody;
          } else {
            this.agencyService
              .find(closingRequest.idxAgency.id)
              .pipe(
                map((subRes: HttpResponse<IAgency>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAgency[]) => (this.idxagencies = concatRes));
          }
        });
    });
  }

  updateForm(closingRequest: IClosingRequest): void {
    this.editForm.patchValue({
      id: closingRequest.id,
      idxAgency: closingRequest.idxAgency,
      closingStatus: closingRequest.closingStatus,
      closingDate: closingRequest.closingDate ? closingRequest.closingDate.format(DATE_TIME_FORMAT) : null,
      revokeCertificate: closingRequest.revokeCertificate,
      revokeCertificateDate: closingRequest.revokeCertificateDate ? closingRequest.revokeCertificateDate.format(DATE_TIME_FORMAT) : null,
      linkEsign: closingRequest.linkEsign,
      linkEsignDate: closingRequest.linkEsignDate ? closingRequest.linkEsignDate.format(DATE_TIME_FORMAT) : null,
      anonymized: closingRequest.anonymized,
      anonymizedDate: closingRequest.anonymizedDate ? closingRequest.anonymizedDate.format(DATE_TIME_FORMAT) : null,
      createBill: closingRequest.createBill,
      createBillDate: closingRequest.createBillDate ? closingRequest.createBillDate.format(DATE_TIME_FORMAT) : null,
      sendBill: closingRequest.sendBill,
      sendBillDate: closingRequest.sendBillDate ? closingRequest.sendBillDate.format(DATE_TIME_FORMAT) : null,
      terminate: closingRequest.terminate,
      terminateDate: closingRequest.terminateDate ? closingRequest.terminateDate.format(DATE_TIME_FORMAT) : null,
      idxAgency: closingRequest.idxAgency
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const closingRequest = this.createFromForm();
    if (closingRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.closingRequestService.update(closingRequest));
    } else {
      this.subscribeToSaveResponse(this.closingRequestService.create(closingRequest));
    }
  }

  private createFromForm(): IClosingRequest {
    return {
      ...new ClosingRequest(),
      id: this.editForm.get(['id'])!.value,
      idxAgency: this.editForm.get(['idxAgency'])!.value,
      closingStatus: this.editForm.get(['closingStatus'])!.value,
      closingDate: this.editForm.get(['closingDate'])!.value
        ? moment(this.editForm.get(['closingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      revokeCertificate: this.editForm.get(['revokeCertificate'])!.value,
      revokeCertificateDate: this.editForm.get(['revokeCertificateDate'])!.value
        ? moment(this.editForm.get(['revokeCertificateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      linkEsign: this.editForm.get(['linkEsign'])!.value,
      linkEsignDate: this.editForm.get(['linkEsignDate'])!.value
        ? moment(this.editForm.get(['linkEsignDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      anonymized: this.editForm.get(['anonymized'])!.value,
      anonymizedDate: this.editForm.get(['anonymizedDate'])!.value
        ? moment(this.editForm.get(['anonymizedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createBill: this.editForm.get(['createBill'])!.value,
      createBillDate: this.editForm.get(['createBillDate'])!.value
        ? moment(this.editForm.get(['createBillDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sendBill: this.editForm.get(['sendBill'])!.value,
      sendBillDate: this.editForm.get(['sendBillDate'])!.value
        ? moment(this.editForm.get(['sendBillDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      terminate: this.editForm.get(['terminate'])!.value,
      terminateDate: this.editForm.get(['terminateDate'])!.value
        ? moment(this.editForm.get(['terminateDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      idxAgency: this.editForm.get(['idxAgency'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClosingRequest>>): void {
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

  trackById(index: number, item: IAgency): any {
    return item.id;
  }
}
