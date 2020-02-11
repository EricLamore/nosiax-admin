import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AdminNosiaxTestModule } from '../../../test.module';
import { AgencyComponent } from 'app/entities/agency/agency.component';
import { AgencyService } from 'app/entities/agency/agency.service';
import { Agency } from 'app/shared/model/agency.model';

describe('Component Tests', () => {
  describe('Agency Management Component', () => {
    let comp: AgencyComponent;
    let fixture: ComponentFixture<AgencyComponent>;
    let service: AgencyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [AgencyComponent]
      })
        .overrideTemplate(AgencyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgencyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgencyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Agency(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agencies && comp.agencies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
