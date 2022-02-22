import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Status } from 'app/entities/enumerations/status.model';
import { IRaRecord, RaRecord } from '../ra-record.model';

import { RaRecordService } from './ra-record.service';

describe('RaRecord Service', () => {
  let service: RaRecordService;
  let httpMock: HttpTestingController;
  let elemDefault: IRaRecord;
  let expectedResult: IRaRecord | IRaRecord[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RaRecordService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      status: Status.DRAFT,
      idUser: 'AAAAAAA',
      identifier: 'AAAAAAA',
      certO: 'AAAAAAA',
      commonName: 'AAAAAAA',
      zipCode: 'AAAAAAA',
      locality: 'AAAAAAA',
      country: 'AAAAAAA',
      lastname: 'AAAAAAA',
      firstname: 'AAAAAAA',
      email: 'AAAAAAA',
      phone: 'AAAAAAA',
      url: 'AAAAAAA',
      idTransaction: 'AAAAAAA',
      transactionStatus: 'AAAAAAA',
      profilCpm: 'AAAAAAA',
      reaso: 'AAAAAAA',
      signatureDate: currentDate,
      validationDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          signatureDate: currentDate.format(DATE_TIME_FORMAT),
          validationDate: currentDate.format(DATE_TIME_FORMAT),
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
          validationDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          signatureDate: currentDate,
          validationDate: currentDate,
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
          id: 1,
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
          validationDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          signatureDate: currentDate,
          validationDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RaRecord', () => {
      const patchObject = Object.assign(
        {
          identifier: 'BBBBBB',
          country: 'BBBBBB',
          phone: 'BBBBBB',
          url: 'BBBBBB',
          profilCpm: 'BBBBBB',
          reaso: 'BBBBBB',
          validationDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new RaRecord()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          signatureDate: currentDate,
          validationDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RaRecord', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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
          validationDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          signatureDate: currentDate,
          validationDate: currentDate,
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

    describe('addRaRecordToCollectionIfMissing', () => {
      it('should add a RaRecord to an empty array', () => {
        const raRecord: IRaRecord = { id: 123 };
        expectedResult = service.addRaRecordToCollectionIfMissing([], raRecord);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(raRecord);
      });

      it('should not add a RaRecord to an array that contains it', () => {
        const raRecord: IRaRecord = { id: 123 };
        const raRecordCollection: IRaRecord[] = [
          {
            ...raRecord,
          },
          { id: 456 },
        ];
        expectedResult = service.addRaRecordToCollectionIfMissing(raRecordCollection, raRecord);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RaRecord to an array that doesn't contain it", () => {
        const raRecord: IRaRecord = { id: 123 };
        const raRecordCollection: IRaRecord[] = [{ id: 456 }];
        expectedResult = service.addRaRecordToCollectionIfMissing(raRecordCollection, raRecord);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(raRecord);
      });

      it('should add only unique RaRecord to an array', () => {
        const raRecordArray: IRaRecord[] = [{ id: 123 }, { id: 456 }, { id: 78245 }];
        const raRecordCollection: IRaRecord[] = [{ id: 123 }];
        expectedResult = service.addRaRecordToCollectionIfMissing(raRecordCollection, ...raRecordArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const raRecord: IRaRecord = { id: 123 };
        const raRecord2: IRaRecord = { id: 456 };
        expectedResult = service.addRaRecordToCollectionIfMissing([], raRecord, raRecord2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(raRecord);
        expect(expectedResult).toContain(raRecord2);
      });

      it('should accept null and undefined values', () => {
        const raRecord: IRaRecord = { id: 123 };
        expectedResult = service.addRaRecordToCollectionIfMissing([], null, raRecord, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(raRecord);
      });

      it('should return initial array if no RaRecord is added', () => {
        const raRecordCollection: IRaRecord[] = [{ id: 123 }];
        expectedResult = service.addRaRecordToCollectionIfMissing(raRecordCollection, undefined, null);
        expect(expectedResult).toEqual(raRecordCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
