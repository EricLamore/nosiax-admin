import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdminNosiaxTestModule } from '../../../test.module';
import { ClosingRequestUpdateComponent } from 'app/entities/closing-request/closing-request-update.component';
import { ClosingRequestService } from 'app/entities/closing-request/closing-request.service';
import { ClosingRequest } from 'app/shared/model/closing-request.model';

describe('Component Tests', () => {
  describe('ClosingRequest Management Update Component', () => {
    let comp: ClosingRequestUpdateComponent;
    let fixture: ComponentFixture<ClosingRequestUpdateComponent>;
    let service: ClosingRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [ClosingRequestUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClosingRequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClosingRequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClosingRequestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClosingRequest(123);
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
        const entity = new ClosingRequest();
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
