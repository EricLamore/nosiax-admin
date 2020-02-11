import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgency, Agency } from 'app/shared/model/agency.model';
import { AgencyService } from './agency.service';

@Component({
  selector: 'jhi-agency-update',
  templateUrl: './agency-update.component.html'
})
export class AgencyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idxAgency: []
  });

  constructor(protected agencyService: AgencyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agency }) => {
      this.updateForm(agency);
    });
  }

  updateForm(agency: IAgency): void {
    this.editForm.patchValue({
      id: agency.id,
      idxAgency: agency.idxAgency
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agency = this.createFromForm();
    if (agency.id !== undefined) {
      this.subscribeToSaveResponse(this.agencyService.update(agency));
    } else {
      this.subscribeToSaveResponse(this.agencyService.create(agency));
    }
  }

  private createFromForm(): IAgency {
    return {
      ...new Agency(),
      id: this.editForm.get(['id'])!.value,
      idxAgency: this.editForm.get(['idxAgency'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgency>>): void {
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
