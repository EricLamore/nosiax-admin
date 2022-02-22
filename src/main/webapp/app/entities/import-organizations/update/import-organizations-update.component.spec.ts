import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ImportOrganizationsService } from '../service/import-organizations.service';
import { IImportOrganizations, ImportOrganizations } from '../import-organizations.model';

import { ImportOrganizationsUpdateComponent } from './import-organizations-update.component';

describe('ImportOrganizations Management Update Component', () => {
  let comp: ImportOrganizationsUpdateComponent;
  let fixture: ComponentFixture<ImportOrganizationsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let importOrganizationsService: ImportOrganizationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ImportOrganizationsUpdateComponent],
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
      .overrideTemplate(ImportOrganizationsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImportOrganizationsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    importOrganizationsService = TestBed.inject(ImportOrganizationsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const importOrganizations: IImportOrganizations = { id: 456 };

      activatedRoute.data = of({ importOrganizations });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(importOrganizations));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ImportOrganizations>>();
      const importOrganizations = { id: 123 };
      jest.spyOn(importOrganizationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ importOrganizations });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: importOrganizations }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(importOrganizationsService.update).toHaveBeenCalledWith(importOrganizations);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ImportOrganizations>>();
      const importOrganizations = new ImportOrganizations();
      jest.spyOn(importOrganizationsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ importOrganizations });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: importOrganizations }));
      saveSubject.complete();

      // THEN
      expect(importOrganizationsService.create).toHaveBeenCalledWith(importOrganizations);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ImportOrganizations>>();
      const importOrganizations = { id: 123 };
      jest.spyOn(importOrganizationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ importOrganizations });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(importOrganizationsService.update).toHaveBeenCalledWith(importOrganizations);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
