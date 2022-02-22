import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationImported, OrganizationImported } from '../organization-imported.model';
import { OrganizationImportedService } from '../service/organization-imported.service';

@Injectable({ providedIn: 'root' })
export class OrganizationImportedRoutingResolveService implements Resolve<IOrganizationImported> {
  constructor(protected service: OrganizationImportedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizationImported> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organizationImported: HttpResponse<OrganizationImported>) => {
          if (organizationImported.body) {
            return of(organizationImported.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrganizationImported());
  }
}
