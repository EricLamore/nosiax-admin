import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOrganizationImported, OrganizationImported } from '../organization-imported.model';
import { OrganizationImportedService } from '../service/organization-imported.service';

import { OrganizationImportedRoutingResolveService } from './organization-imported-routing-resolve.service';

describe('OrganizationImported routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OrganizationImportedRoutingResolveService;
  let service: OrganizationImportedService;
  let resultOrganizationImported: IOrganizationImported | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(OrganizationImportedRoutingResolveService);
    service = TestBed.inject(OrganizationImportedService);
    resultOrganizationImported = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizationImported returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationImported = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationImported).toEqual({ id: 123 });
    });

    it('should return new IOrganizationImported if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationImported = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOrganizationImported).toEqual(new OrganizationImported());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OrganizationImported })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationImported = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationImported).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
