import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
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
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RaRecordService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RaRecord(
        0,
        Status.DRAFT,
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
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            signatureDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RaRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            signatureDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            signatureDate: currentDate,
            validationDate: currentDate
          },
          returnedFromService
        );

        service.create(new RaRecord()).subscribe(resp => (expectedResult = resp.body));

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
            zipCode: 'BBBBBB',
            locality: 'BBBBBB',
            country: 'BBBBBB',
            lastname: 'BBBBBB',
            firstname: 'BBBBBB',
            email: 'BBBBBB',
            phone: 'BBBBBB',
            url: 'BBBBBB',
            idTransaction: 'BBBBBB',
            transactionStatus: 'BBBBBB',
            profilCpm: 'BBBBBB',
            reaso: 'BBBBBB',
            signatureDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            signatureDate: currentDate,
            validationDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

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
            zipCode: 'BBBBBB',
            locality: 'BBBBBB',
            country: 'BBBBBB',
            lastname: 'BBBBBB',
            firstname: 'BBBBBB',
            email: 'BBBBBB',
            phone: 'BBBBBB',
            url: 'BBBBBB',
            idTransaction: 'BBBBBB',
            transactionStatus: 'BBBBBB',
            profilCpm: 'BBBBBB',
            reaso: 'BBBBBB',
            signatureDate: currentDate.format(DATE_TIME_FORMAT),
            validationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            signatureDate: currentDate,
            validationDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

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
