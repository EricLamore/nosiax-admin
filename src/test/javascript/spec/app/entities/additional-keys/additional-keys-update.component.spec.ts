import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdminNosiaxTestModule } from '../../../test.module';
import { AdditionalKeysUpdateComponent } from 'app/entities/additional-keys/additional-keys-update.component';
import { AdditionalKeysService } from 'app/entities/additional-keys/additional-keys.service';
import { AdditionalKeys } from 'app/shared/model/additional-keys.model';

describe('Component Tests', () => {
  describe('AdditionalKeys Management Update Component', () => {
    let comp: AdditionalKeysUpdateComponent;
    let fixture: ComponentFixture<AdditionalKeysUpdateComponent>;
    let service: AdditionalKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [AdditionalKeysUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdditionalKeysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdditionalKeysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdditionalKeysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdditionalKeys(123);
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
        const entity = new AdditionalKeys();
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
