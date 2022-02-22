import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RaRecordDetailComponent } from './ra-record-detail.component';

describe('RaRecord Management Detail Component', () => {
  let comp: RaRecordDetailComponent;
  let fixture: ComponentFixture<RaRecordDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RaRecordDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ raRecord: { id: 123 } }) },
        },
      ],
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
      expect(comp.raRecord).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
