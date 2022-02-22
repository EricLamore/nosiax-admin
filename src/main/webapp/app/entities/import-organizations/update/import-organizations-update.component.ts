import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IImportOrganizations, ImportOrganizations } from '../import-organizations.model';
import { ImportOrganizationsService } from '../service/import-organizations.service';

@Component({
  selector: 'jhi-import-organizations-update',
  templateUrl: './import-organizations-update.component.html',
})
export class ImportOrganizationsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    orgMasterId: [],
    orgName: [],
    partenaire: [],
    profile: [],
    launchCreationDate: [],
  });

  constructor(
    protected importOrganizationsService: ImportOrganizationsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ importOrganizations }) => {
      this.updateForm(importOrganizations);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const importOrganizations = this.createFromForm();
    if (importOrganizations.id !== undefined) {
      this.subscribeToSaveResponse(this.importOrganizationsService.update(importOrganizations));
    } else {
      this.subscribeToSaveResponse(this.importOrganizationsService.create(importOrganizations));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImportOrganizations>>): void {
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

  protected updateForm(importOrganizations: IImportOrganizations): void {
    this.editForm.patchValue({
      id: importOrganizations.id,
      orgMasterId: importOrganizations.orgMasterId,
      orgName: importOrganizations.orgName,
      partenaire: importOrganizations.partenaire,
      profile: importOrganizations.profile,
      launchCreationDate: importOrganizations.launchCreationDate,
    });
  }

  protected createFromForm(): IImportOrganizations {
    return {
      ...new ImportOrganizations(),
      id: this.editForm.get(['id'])!.value,
      orgMasterId: this.editForm.get(['orgMasterId'])!.value,
      orgName: this.editForm.get(['orgName'])!.value,
      partenaire: this.editForm.get(['partenaire'])!.value,
      profile: this.editForm.get(['profile'])!.value,
      launchCreationDate: this.editForm.get(['launchCreationDate'])!.value,
    };
  }
}
