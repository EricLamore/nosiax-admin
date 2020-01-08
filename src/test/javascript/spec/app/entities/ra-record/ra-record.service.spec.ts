import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { RaRecordService } from 'app/entities/ra-record/ra-record.service';
import { IRaRecord, RaRecord } from 'app/shared/model/ra-record.model';
import { Status } from 'app/shared/model/enumerations/status.model';

describe('Service Tests', () => {
  describe('RaRecord Service', () => {
    let injector: TestBed;
    let service: RaRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: IRaRecord;
    let expectedResult: IRaRecord | IRaRecord[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RaRecordService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RaRecord(
        0,
        Status.NONE,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RaRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new RaRecord())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RaRecord', () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            idUser: 'BBBBBB',
            identifier: 'BBBBBB',
            certO: 'BBBBBB',
            commonName: 'BBBBBB',
            locality: 'BBBBBB',
            country: 'BBBBBB',
            lastname: 'BBBBBB',
            firstname: 'BBBBBB',
            email: 'BBBBBB',
            phone: 'BBBBBB',
            url: 'BBBBBB',
            idTransaction: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RaRecord', () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            idUser: 'BBBBBB',
            identifier: 'BBBBBB',
            certO: 'BBBBBB',
            commonName: 'BBBBBB',
            locality: 'BBBBBB',
            country: 'BBBBBB',
            lastname: 'BBBBBB',
            firstname: 'BBBBBB',
            email: 'BBBBBB',
            phone: 'BBBBBB',
            url: 'BBBBBB',
            idTransaction: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RaRecord', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
