import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RaRecordService } from '../service/ra-record.service';
import { IRaRecord, RaRecord } from '../ra-record.model';

import { RaRecordUpdateComponent } from './ra-record-update.component';

describe('RaRecord Management Update Component', () => {
  let comp: RaRecordUpdateComponent;
  let fixture: ComponentFixture<RaRecordUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let raRecordService: RaRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RaRecordUpdateComponent],
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
      .overrideTemplate(RaRecordUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RaRecordUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    raRecordService = TestBed.inject(RaRecordService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const raRecord: IRaRecord = { id: 456 };

      activatedRoute.data = of({ raRecord });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(raRecord));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RaRecord>>();
      const raRecord = { id: 123 };
      jest.spyOn(raRecordService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ raRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: raRecord }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(raRecordService.update).toHaveBeenCalledWith(raRecord);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RaRecord>>();
      const raRecord = new RaRecord();
      jest.spyOn(raRecordService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ raRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: raRecord }));
      saveSubject.complete();

      // THEN
      expect(raRecordService.create).toHaveBeenCalledWith(raRecord);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RaRecord>>();
      const raRecord = { id: 123 };
      jest.spyOn(raRecordService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ raRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(raRecordService.update).toHaveBeenCalledWith(raRecord);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
