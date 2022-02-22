jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AdditionalKeysService } from '../service/additional-keys.service';

import { AdditionalKeysDeleteDialogComponent } from './additional-keys-delete-dialog.component';

describe('AdditionalKeys Management Delete Component', () => {
  let comp: AdditionalKeysDeleteDialogComponent;
  let fixture: ComponentFixture<AdditionalKeysDeleteDialogComponent>;
  let service: AdditionalKeysService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AdditionalKeysDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AdditionalKeysDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdditionalKeysDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AdditionalKeysService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
