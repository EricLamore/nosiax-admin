import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdditionalKeysDetailComponent } from './additional-keys-detail.component';

describe('AdditionalKeys Management Detail Component', () => {
  let comp: AdditionalKeysDetailComponent;
  let fixture: ComponentFixture<AdditionalKeysDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdditionalKeysDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ additionalKeys: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdditionalKeysDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdditionalKeysDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load additionalKeys on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.additionalKeys).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
