import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationImportedDetailComponent } from './organization-imported-detail.component';

describe('OrganizationImported Management Detail Component', () => {
  let comp: OrganizationImportedDetailComponent;
  let fixture: ComponentFixture<OrganizationImportedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationImportedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organizationImported: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationImportedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationImportedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organizationImported on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organizationImported).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
