import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminNosiaxTestModule } from '../../../test.module';
import { RaRecordDetailComponent } from 'app/entities/ra-record/ra-record-detail.component';
import { RaRecord } from 'app/shared/model/ra-record.model';

describe('Component Tests', () => {
  describe('RaRecord Management Detail Component', () => {
    let comp: RaRecordDetailComponent;
    let fixture: ComponentFixture<RaRecordDetailComponent>;
    const route = ({ data: of({ raRecord: new RaRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [RaRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RaRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RaRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load raRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.raRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
