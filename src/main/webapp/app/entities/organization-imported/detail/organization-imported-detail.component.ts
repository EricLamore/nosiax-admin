import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationImported } from '../organization-imported.model';

@Component({
  selector: 'jhi-organization-imported-detail',
  templateUrl: './organization-imported-detail.component.html',
})
export class OrganizationImportedDetailComponent implements OnInit {
  organizationImported: IOrganizationImported | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationImported }) => {
      this.organizationImported = organizationImported;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
