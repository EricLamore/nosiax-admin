import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdminNosiaxTestModule } from '../../../test.module';
import { RaRecordUpdateComponent } from 'app/entities/ra-record/ra-record-update.component';
import { RaRecordService } from 'app/entities/ra-record/ra-record.service';
import { RaRecord } from 'app/shared/model/ra-record.model';

describe('Component Tests', () => {
  describe('RaRecord Management Update Component', () => {
    let comp: RaRecordUpdateComponent;
    let fixture: ComponentFixture<RaRecordUpdateComponent>;
    let service: RaRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [RaRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RaRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RaRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RaRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RaRecord(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RaRecord();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
