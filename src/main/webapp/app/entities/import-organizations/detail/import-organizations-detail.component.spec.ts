import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImportOrganizationsDetailComponent } from './import-organizations-detail.component';

describe('ImportOrganizations Management Detail Component', () => {
  let comp: ImportOrganizationsDetailComponent;
  let fixture: ComponentFixture<ImportOrganizationsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ImportOrganizationsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ importOrganizations: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ImportOrganizationsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ImportOrganizationsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load importOrganizations on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.importOrganizations).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
