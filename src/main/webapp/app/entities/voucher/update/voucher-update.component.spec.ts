import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VoucherService } from '../service/voucher.service';
import { IVoucher, Voucher } from '../voucher.model';
import { IRaRecord } from 'app/entities/ra-record/ra-record.model';
import { RaRecordService } from 'app/entities/ra-record/service/ra-record.service';

import { VoucherUpdateComponent } from './voucher-update.component';

describe('Voucher Management Update Component', () => {
  let comp: VoucherUpdateComponent;
  let fixture: ComponentFixture<VoucherUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let voucherService: VoucherService;
  let raRecordService: RaRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VoucherUpdateComponent],
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
      .overrideTemplate(VoucherUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VoucherUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    voucherService = TestBed.inject(VoucherService);
    raRecordService = TestBed.inject(RaRecordService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RaRecord query and add missing value', () => {
      const voucher: IVoucher = { id: 456 };
      const raRecord: IRaRecord = { id: 45849 };
      voucher.raRecord = raRecord;

      const raRecordCollection: IRaRecord[] = [{ id: 50523 }];
      jest.spyOn(raRecordService, 'query').mockReturnValue(of(new HttpResponse({ body: raRecordCollection })));
      const additionalRaRecords = [raRecord];
      const expectedCollection: IRaRecord[] = [...additionalRaRecords, ...raRecordCollection];
      jest.spyOn(raRecordService, 'addRaRecordToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      expect(raRecordService.query).toHaveBeenCalled();
      expect(raRecordService.addRaRecordToCollectionIfMissing).toHaveBeenCalledWith(raRecordCollection, ...additionalRaRecords);
      expect(comp.raRecordsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const voucher: IVoucher = { id: 456 };
      const raRecord: IRaRecord = { id: 58237 };
      voucher.raRecord = raRecord;

      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(voucher));
      expect(comp.raRecordsSharedCollection).toContain(raRecord);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Voucher>>();
      const voucher = { id: 123 };
      jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucher }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(voucherService.update).toHaveBeenCalledWith(voucher);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Voucher>>();
      const voucher = new Voucher();
      jest.spyOn(voucherService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucher }));
      saveSubject.complete();

      // THEN
      expect(voucherService.create).toHaveBeenCalledWith(voucher);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Voucher>>();
      const voucher = { id: 123 };
      jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(voucherService.update).toHaveBeenCalledWith(voucher);
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
