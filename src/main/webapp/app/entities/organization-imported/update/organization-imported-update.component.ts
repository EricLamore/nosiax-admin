import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IOrganizationImported, OrganizationImported } from '../organization-imported.model';
import { OrganizationImportedService } from '../service/organization-imported.service';
import { IImportOrganizations } from 'app/entities/import-organizations/import-organizations.model';
import { ImportOrganizationsService } from 'app/entities/import-organizations/service/import-organizations.service';
import { Language } from 'app/entities/enumerations/language.model';

@Component({
  selector: 'jhi-organization-imported-update',
  templateUrl: './organization-imported-update.component.html',
})
export class OrganizationImportedUpdateComponent implements OnInit {
  isSaving = false;
  languageValues = Object.keys(Language);

  importOrganizationsSharedCollection: IImportOrganizations[] = [];

  editForm = this.fb.group({
    id: [],
    client: [],
    displayname: [],
    logo: [],
    profileName: [],
    consoSig: [],
    consoTimes: [],
    consoSeal: [],
    technicalAccountCreation: [],
    isTechnicalAccountAdmin: [],
    isTechnicalAccount: [],
    isOperator: [],
    technicalAccountFirstname: [],
    technicalAccountLastname: [],
    technicalAccountMail: [],
    language: [],
    orgCreated: [],
    technicalAccountCreated: [],
    consoCreated: [],
    createDate: [],
    employee: [],
  });

  constructor(
    protected organizationImportedService: OrganizationImportedService,
    protected importOrganizationsService: ImportOrganizationsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationImported }) => {
      this.updateForm(organizationImported);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationImported = this.createFromForm();
    if (organizationImported.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationImportedService.update(organizationImported));
    } else {
      this.subscribeToSaveResponse(this.organizationImportedService.create(organizationImported));
    }
  }

  trackImportOrganizationsById(index: number, item: IImportOrganizations): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationImported>>): void {
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

  protected updateForm(organizationImported: IOrganizationImported): void {
    this.editForm.patchValue({
      id: organizationImported.id,
      client: organizationImported.client,
      displayname: organizationImported.displayname,
      logo: organizationImported.logo,
      profileName: organizationImported.profileName,
      consoSig: organizationImported.consoSig,
      consoTimes: organizationImported.consoTimes,
      consoSeal: organizationImported.consoSeal,
      technicalAccountCreation: organizationImported.technicalAccountCreation,
      isTechnicalAccountAdmin: organizationImported.isTechnicalAccountAdmin,
      isTechnicalAccount: organizationImported.isTechnicalAccount,
      isOperator: organizationImported.isOperator,
      technicalAccountFirstname: organizationImported.technicalAccountFirstname,
      technicalAccountLastname: organizationImported.technicalAccountLastname,
      technicalAccountMail: organizationImported.technicalAccountMail,
      language: organizationImported.language,
      orgCreated: organizationImported.orgCreated,
      technicalAccountCreated: organizationImported.technicalAccountCreated,
      consoCreated: organizationImported.consoCreated,
      createDate: organizationImported.createDate,
      employee: organizationImported.employee,
    });

    this.importOrganizationsSharedCollection = this.importOrganizationsService.addImportOrganizationsToCollectionIfMissing(
      this.importOrganizationsSharedCollection,
      organizationImported.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.importOrganizationsService
      .query()
      .pipe(map((res: HttpResponse<IImportOrganizations[]>) => res.body ?? []))
      .pipe(
        map((importOrganizations: IImportOrganizations[]) =>
          this.importOrganizationsService.addImportOrganizationsToCollectionIfMissing(
            importOrganizations,
            this.editForm.get('employee')!.value
          )
        )
      )
      .subscribe((importOrganizations: IImportOrganizations[]) => (this.importOrganizationsSharedCollection = importOrganizations));
  }

  protected createFromForm(): IOrganizationImported {
    return {
      ...new OrganizationImported(),
      id: this.editForm.get(['id'])!.value,
      client: this.editForm.get(['client'])!.value,
      displayname: this.editForm.get(['displayname'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      profileName: this.editForm.get(['profileName'])!.value,
      consoSig: this.editForm.get(['consoSig'])!.value,
      consoTimes: this.editForm.get(['consoTimes'])!.value,
      consoSeal: this.editForm.get(['consoSeal'])!.value,
      technicalAccountCreation: this.editForm.get(['technicalAccountCreation'])!.value,
      isTechnicalAccountAdmin: this.editForm.get(['isTechnicalAccountAdmin'])!.value,
      isTechnicalAccount: this.editForm.get(['isTechnicalAccount'])!.value,
      isOperator: this.editForm.get(['isOperator'])!.value,
      technicalAccountFirstname: this.editForm.get(['technicalAccountFirstname'])!.value,
      technicalAccountLastname: this.editForm.get(['technicalAccountLastname'])!.value,
      technicalAccountMail: this.editForm.get(['technicalAccountMail'])!.value,
      language: this.editForm.get(['language'])!.value,
      orgCreated: this.editForm.get(['orgCreated'])!.value,
      technicalAccountCreated: this.editForm.get(['technicalAccountCreated'])!.value,
      consoCreated: this.editForm.get(['consoCreated'])!.value,
      createDate: this.editForm.get(['createDate'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }
}
