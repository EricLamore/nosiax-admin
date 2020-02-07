import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AdminNosiaxTestModule } from '../../../test.module';
import { AdditionalKeysComponent } from 'app/entities/additional-keys/additional-keys.component';
import { AdditionalKeysService } from 'app/entities/additional-keys/additional-keys.service';
import { AdditionalKeys } from 'app/shared/model/additional-keys.model';

describe('Component Tests', () => {
  describe('AdditionalKeys Management Component', () => {
    let comp: AdditionalKeysComponent;
    let fixture: ComponentFixture<AdditionalKeysComponent>;
    let service: AdditionalKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [AdditionalKeysComponent]
      })
        .overrideTemplate(AdditionalKeysComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdditionalKeysComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdditionalKeysService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AdditionalKeys(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.additionalKeys && comp.additionalKeys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
