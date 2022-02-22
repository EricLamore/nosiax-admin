import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImportOrganizations } from '../import-organizations.model';

@Component({
  selector: 'jhi-import-organizations-detail',
  templateUrl: './import-organizations-detail.component.html',
})
export class ImportOrganizationsDetailComponent implements OnInit {
  importOrganizations: IImportOrganizations | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ importOrganizations }) => {
      this.importOrganizations = importOrganizations;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
