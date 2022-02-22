import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationImportedService } from '../service/organization-imported.service';
import { IOrganizationImported, OrganizationImported } from '../organization-imported.model';
import { IImportOrganizations } from 'app/entities/import-organizations/import-organizations.model';
import { ImportOrganizationsService } from 'app/entities/import-organizations/service/import-organizations.service';

import { OrganizationImportedUpdateComponent } from './organization-imported-update.component';

describe('OrganizationImported Management Update Component', () => {
  let comp: OrganizationImportedUpdateComponent;
  let fixture: ComponentFixture<OrganizationImportedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationImportedService: OrganizationImportedService;
  let importOrganizationsService: ImportOrganizationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationImportedUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(OrganizationImportedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationImportedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationImportedService = TestBed.inject(OrganizationImportedService);
    importOrganizationsService = TestBed.inject(ImportOrganizationsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ImportOrganizations query and add missing value', () => {
      const organizationImported: IOrganizationImported = { id: 456 };
      const organizationImported: IImportOrganizations = { id: 77298 };
      organizationImported.organizationImported = organizationImported;

      const importOrganizationsCollection: IImportOrganizations[] = [{ id: 7717 }];
      jest.spyOn(importOrganizationsService, 'query').mockReturnValue(of(new HttpResponse({ body: importOrganizationsCollection })));
      const additionalImportOrganizations = [organizationImported];
      const expectedCollection: IImportOrganizations[] = [...additionalImportOrganizations, ...importOrganizationsCollection];
      jest.spyOn(importOrganizationsService, 'addImportOrganizationsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationImported });
      comp.ngOnInit();

      expect(importOrganizationsService.query).toHaveBeenCalled();
      expect(importOrganizationsService.addImportOrganizationsToCollectionIfMissing).toHaveBeenCalledWith(
        importOrganizationsCollection,
        ...additionalImportOrganizations
      );
      expect(comp.importOrganizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organizationImported: IOrganizationImported = { id: 456 };
      const organizationImported: IImportOrganizations = { id: 24057 };
      organizationImported.organizationImported = organizationImported;

      activatedRoute.data = of({ organizationImported });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(organizationImported));
      expect(comp.importOrganizationsSharedCollection).toContain(organizationImported);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrganizationImported>>();
      const organizationImported = { id: 123 };
      jest.spyOn(organizationImportedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationImported });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationImported }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationImportedService.update).toHaveBeenCalledWith(organizationImported);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrganizationImported>>();
      const organizationImported = new OrganizationImported();
      jest.spyOn(organizationImportedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationImported });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationImported }));
      saveSubject.complete();

      // THEN
      expect(organizationImportedService.create).toHaveBeenCalledWith(organizationImported);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrganizationImported>>();
      const organizationImported = { id: 123 };
      jest.spyOn(organizationImportedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationImported });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationImportedService.update).toHaveBeenCalledWith(organizationImported);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackImportOrganizationsById', () => {
      it('Should return tracked ImportOrganizations primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackImportOrganizationsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
