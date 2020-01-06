import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminNosiaxTestModule } from '../../../test.module';
import { AdditionalKeysDetailComponent } from 'app/entities/additional-keys/additional-keys-detail.component';
import { AdditionalKeys } from 'app/shared/model/additional-keys.model';

describe('Component Tests', () => {
  describe('AdditionalKeys Management Detail Component', () => {
    let comp: AdditionalKeysDetailComponent;
    let fixture: ComponentFixture<AdditionalKeysDetailComponent>;
    const route = ({ data: of({ additionalKeys: new AdditionalKeys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdminNosiaxTestModule],
        declarations: [AdditionalKeysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
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
        expect(comp.additionalKeys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
