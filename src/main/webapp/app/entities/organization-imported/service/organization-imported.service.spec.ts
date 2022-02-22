import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Language } from 'app/entities/enumerations/language.model';
import { IOrganizationImported, OrganizationImported } from '../organization-imported.model';

import { OrganizationImportedService } from './organization-imported.service';

describe('OrganizationImported Service', () => {
  let service: OrganizationImportedService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrganizationImported;
  let expectedResult: IOrganizationImported | IOrganizationImported[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationImportedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      client: 'AAAAAAA',
      displayname: 'AAAAAAA',
      logo: 'AAAAAAA',
      profileName: 'AAAAAAA',
      consoSig: false,
      consoTimes: false,
      consoSeal: false,
      technicalAccountCreation: false,
      isTechnicalAccountAdmin: false,
      isTechnicalAccount: false,
      isOperator: false,
      technicalAccountFirstname: 'AAAAAAA',
      technicalAccountLastname: 'AAAAAAA',
      technicalAccountMail: 'AAAAAAA',
      language: Language.FRENCH,
      orgCreated: false,
      technicalAccountCreated: false,
      consoCreated: false,
      createDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a OrganizationImported', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDate: currentDate,
        },
        returnedFromService
      );

      service.create(new OrganizationImported()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationImported', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          client: 'BBBBBB',
          displayname: 'BBBBBB',
          logo: 'BBBBBB',
          profileName: 'BBBBBB',
          consoSig: true,
          consoTimes: true,
          consoSeal: true,
          technicalAccountCreation: true,
          isTechnicalAccountAdmin: true,
          isTechnicalAccount: true,
          isOperator: true,
          technicalAccountFirstname: 'BBBBBB',
          technicalAccountLastname: 'BBBBBB',
          technicalAccountMail: 'BBBBBB',
          language: 'BBBBBB',
          orgCreated: true,
          technicalAccountCreated: true,
          consoCreated: true,
          createDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationImported', () => {
      const patchObject = Object.assign(
        {
          displayname: 'BBBBBB',
          logo: 'BBBBBB',
          technicalAccountCreation: true,
          isTechnicalAccountAdmin: true,
          isTechnicalAccount: true,
          technicalAccountMail: 'BBBBBB',
          technicalAccountCreated: true,
          consoCreated: true,
          createDate: currentDate.format(DATE_FORMAT),
        },
        new OrganizationImported()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationImported', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          client: 'BBBBBB',
          displayname: 'BBBBBB',
          logo: 'BBBBBB',
          profileName: 'BBBBBB',
          consoSig: true,
          consoTimes: true,
          consoSeal: true,
          technicalAccountCreation: true,
          isTechnicalAccountAdmin: true,
          isTechnicalAccount: true,
          isOperator: true,
          technicalAccountFirstname: 'BBBBBB',
          technicalAccountLastname: 'BBBBBB',
          technicalAccountMail: 'BBBBBB',
          language: 'BBBBBB',
          orgCreated: true,
          technicalAccountCreated: true,
          consoCreated: true,
          createDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a OrganizationImported', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrganizationImportedToCollectionIfMissing', () => {
      it('should add a OrganizationImported to an empty array', () => {
        const organizationImported: IOrganizationImported = { id: 123 };
        expectedResult = service.addOrganizationImportedToCollectionIfMissing([], organizationImported);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationImported);
      });

      it('should not add a OrganizationImported to an array that contains it', () => {
        const organizationImported: IOrganizationImported = { id: 123 };
        const organizationImportedCollection: IOrganizationImported[] = [
          {
            ...organizationImported,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrganizationImportedToCollectionIfMissing(organizationImportedCollection, organizationImported);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationImported to an array that doesn't contain it", () => {
        const organizationImported: IOrganizationImported = { id: 123 };
        const organizationImportedCollection: IOrganizationImported[] = [{ id: 456 }];
        expectedResult = service.addOrganizationImportedToCollectionIfMissing(organizationImportedCollection, organizationImported);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationImported);
      });

      it('should add only unique OrganizationImported to an array', () => {
        const organizationImportedArray: IOrganizationImported[] = [{ id: 123 }, { id: 456 }, { id: 3629 }];
        const organizationImportedCollection: IOrganizationImported[] = [{ id: 123 }];
        expectedResult = service.addOrganizationImportedToCollectionIfMissing(organizationImportedCollection, ...organizationImportedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationImported: IOrganizationImported = { id: 123 };
        const organizationImported2: IOrganizationImported = { id: 456 };
        expectedResult = service.addOrganizationImportedToCollectionIfMissing([], organizationImported, organizationImported2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationImported);
        expect(expectedResult).toContain(organizationImported2);
      });

      it('should accept null and undefined values', () => {
        const organizationImported: IOrganizationImported = { id: 123 };
        expectedResult = service.addOrganizationImportedToCollectionIfMissing([], null, organizationImported, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationImported);
      });

      it('should return initial array if no OrganizationImported is added', () => {
        const organizationImportedCollection: IOrganizationImported[] = [{ id: 123 }];
        expectedResult = service.addOrganizationImportedToCollectionIfMissing(organizationImportedCollection, undefined, null);
        expect(expectedResult).toEqual(organizationImportedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
