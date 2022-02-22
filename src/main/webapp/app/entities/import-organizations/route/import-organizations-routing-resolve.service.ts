import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImportOrganizations, ImportOrganizations } from '../import-organizations.model';
import { ImportOrganizationsService } from '../service/import-organizations.service';

@Injectable({ providedIn: 'root' })
export class ImportOrganizationsRoutingResolveService implements Resolve<IImportOrganizations> {
  constructor(protected service: ImportOrganizationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImportOrganizations> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((importOrganizations: HttpResponse<ImportOrganizations>) => {
          if (importOrganizations.body) {
            return of(importOrganizations.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ImportOrganizations());
  }
}
