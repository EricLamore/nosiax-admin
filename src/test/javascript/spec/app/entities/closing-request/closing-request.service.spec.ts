import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ClosingRequestService } from 'app/entities/closing-request/closing-request.service';
import { IClosingRequest, ClosingRequest } from 'app/shared/model/closing-request.model';
import { ClosingStatus } from 'app/shared/model/enumerations/closing-status.model';

describe('Service Tests', () => {
  describe('ClosingRequest Service', () => {
    let injector: TestBed;
    let service: ClosingRequestService;
    let httpMock: HttpTestingController;
    let elemDefault: IClosingRequest;
    let expectedResult: IClosingRequest | IClosingRequest[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ClosingRequestService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ClosingRequest(
        0,
        currentDate,
        ClosingStatus.CREATED,
        currentDate,
        false,
        currentDate,
        false,
        currentDate,
        false,
        currentDate,
        false,
        currentDate,
        false,
        currentDate,
        false,
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            idxAgency: currentDate.format(DATE_TIME_FORMAT),
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            revokeCertificateDate: currentDate.format(DATE_TIME_FORMAT),
            linkEsignDate: currentDate.format(DATE_TIME_FORMAT),
            anonymizedDate: currentDate.format(DATE_TIME_FORMAT),
            createBillDate: currentDate.format(DATE_TIME_FORMAT),
            sendBillDate: currentDate.format(DATE_TIME_FORMAT),
            terminateDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ClosingRequest', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            idxAgency: currentDate.format(DATE_TIME_FORMAT),
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            revokeCertificateDate: currentDate.format(DATE_TIME_FORMAT),
            linkEsignDate: currentDate.format(DATE_TIME_FORMAT),
            anonymizedDate: currentDate.format(DATE_TIME_FORMAT),
            createBillDate: currentDate.format(DATE_TIME_FORMAT),
            sendBillDate: currentDate.format(DATE_TIME_FORMAT),
            terminateDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            idxAgency: currentDate,
            closingDate: currentDate,
            revokeCertificateDate: currentDate,
            linkEsignDate: currentDate,
            anonymizedDate: currentDate,
            createBillDate: currentDate,
            sendBillDate: currentDate,
            terminateDate: currentDate
          },
          returnedFromService
        );

        service.create(new ClosingRequest()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ClosingRequest', () => {
        const returnedFromService = Object.assign(
          {
            idxAgency: currentDate.format(DATE_TIME_FORMAT),
            closingStatus: 'BBBBBB',
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            revokeCertificate: true,
            revokeCertificateDate: currentDate.format(DATE_TIME_FORMAT),
            linkEsign: true,
            linkEsignDate: currentDate.format(DATE_TIME_FORMAT),
            anonymized: true,
            anonymizedDate: currentDate.format(DATE_TIME_FORMAT),
            createBill: true,
            createBillDate: currentDate.format(DATE_TIME_FORMAT),
            sendBill: true,
            sendBillDate: currentDate.format(DATE_TIME_FORMAT),
            terminate: true,
            terminateDate: currentDate.format(DATE_TIME_FORMAT),
            idTransactionClosing: 'BBBBBB',
            urlTransactionClosing: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            idxAgency: currentDate,
            closingDate: currentDate,
            revokeCertificateDate: currentDate,
            linkEsignDate: currentDate,
            anonymizedDate: currentDate,
            createBillDate: currentDate,
            sendBillDate: currentDate,
            terminateDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ClosingRequest', () => {
        const returnedFromService = Object.assign(
          {
            idxAgency: currentDate.format(DATE_TIME_FORMAT),
            closingStatus: 'BBBBBB',
            closingDate: currentDate.format(DATE_TIME_FORMAT),
            revokeCertificate: true,
            revokeCertificateDate: currentDate.format(DATE_TIME_FORMAT),
            linkEsign: true,
            linkEsignDate: currentDate.format(DATE_TIME_FORMAT),
            anonymized: true,
            anonymizedDate: currentDate.format(DATE_TIME_FORMAT),
            createBill: true,
            createBillDate: currentDate.format(DATE_TIME_FORMAT),
            sendBill: true,
            sendBillDate: currentDate.format(DATE_TIME_FORMAT),
            terminate: true,
            terminateDate: currentDate.format(DATE_TIME_FORMAT),
            idTransactionClosing: 'BBBBBB',
            urlTransactionClosing: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            idxAgency: currentDate,
            closingDate: currentDate,
            revokeCertificateDate: currentDate,
            linkEsignDate: currentDate,
            anonymizedDate: currentDate,
            createBillDate: currentDate,
            sendBillDate: currentDate,
            terminateDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ClosingRequest', () => {
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
