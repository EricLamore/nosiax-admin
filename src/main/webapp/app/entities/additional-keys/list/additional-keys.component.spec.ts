import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AdditionalKeysService } from '../service/additional-keys.service';

import { AdditionalKeysComponent } from './additional-keys.component';

describe('AdditionalKeys Management Component', () => {
  let comp: AdditionalKeysComponent;
  let fixture: ComponentFixture<AdditionalKeysComponent>;
  let service: AdditionalKeysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AdditionalKeysComponent],
    })
      .overrideTemplate(AdditionalKeysComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdditionalKeysComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AdditionalKeysService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.additionalKeys?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
