import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminNosiaxTestModule } from '../../../test.module';
import { ClosingRequestDetailComponent } from 'app/entities/closing-request/closing-request-detail.component';
import { ClosingRequest } from 'app/shared/model/closing-request.model';

describe('Component Tests', () => {
  describe('ClosingRequest Management Detail Component', () => {
    let comp: ClosingRequestDetailComponent;
    let fixture: ComponentFixture<ClosingRequestDetailComponent>;
    const route = ({ data: of({ closingRequest: new ClosingRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [ClosingRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClosingRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClosingRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load closingRequest on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.closingRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
