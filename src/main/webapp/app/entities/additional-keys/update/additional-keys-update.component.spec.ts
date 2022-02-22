import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdditionalKeysService } from '../service/additional-keys.service';
import { IAdditionalKeys, AdditionalKeys } from '../additional-keys.model';
import { IRaRecord } from 'app/entities/ra-record/ra-record.model';
import { RaRecordService } from 'app/entities/ra-record/service/ra-record.service';

import { AdditionalKeysUpdateComponent } from './additional-keys-update.component';

describe('AdditionalKeys Management Update Component', () => {
  let comp: AdditionalKeysUpdateComponent;
  let fixture: ComponentFixture<AdditionalKeysUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let additionalKeysService: AdditionalKeysService;
  let raRecordService: RaRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdditionalKeysUpdateComponent],
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
      .overrideTemplate(AdditionalKeysUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdditionalKeysUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    additionalKeysService = TestBed.inject(AdditionalKeysService);
    raRecordService = TestBed.inject(RaRecordService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RaRecord query and add missing value', () => {
      const additionalKeys: IAdditionalKeys = { id: 456 };
      const raRecord: IRaRecord = { id: 12468 };
      additionalKeys.raRecord = raRecord;

      const raRecordCollection: IRaRecord[] = [{ id: 37319 }];
      jest.spyOn(raRecordService, 'query').mockReturnValue(of(new HttpResponse({ body: raRecordCollection })));
      const additionalRaRecords = [raRecord];
      const expectedCollection: IRaRecord[] = [...additionalRaRecords, ...raRecordCollection];
      jest.spyOn(raRecordService, 'addRaRecordToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ additionalKeys });
      comp.ngOnInit();

      expect(raRecordService.query).toHaveBeenCalled();
      expect(raRecordService.addRaRecordToCollectionIfMissing).toHaveBeenCalledWith(raRecordCollection, ...additionalRaRecords);
      expect(comp.raRecordsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const additionalKeys: IAdditionalKeys = { id: 456 };
      const raRecord: IRaRecord = { id: 51905 };
      additionalKeys.raRecord = raRecord;

      activatedRoute.data = of({ additionalKeys });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(additionalKeys));
      expect(comp.raRecordsSharedCollection).toContain(raRecord);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdditionalKeys>>();
      const additionalKeys = { id: 123 };
      jest.spyOn(additionalKeysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ additionalKeys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: additionalKeys }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(additionalKeysService.update).toHaveBeenCalledWith(additionalKeys);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdditionalKeys>>();
      const additionalKeys = new AdditionalKeys();
      jest.spyOn(additionalKeysService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ additionalKeys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: additionalKeys }));
      saveSubject.complete();

      // THEN
      expect(additionalKeysService.create).toHaveBeenCalledWith(additionalKeys);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdditionalKeys>>();
      const additionalKeys = { id: 123 };
      jest.spyOn(additionalKeysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ additionalKeys });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(additionalKeysService.update).toHaveBeenCalledWith(additionalKeys);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRaRecordById', () => {
      it('Should return tracked RaRecord primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRaRecordById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
