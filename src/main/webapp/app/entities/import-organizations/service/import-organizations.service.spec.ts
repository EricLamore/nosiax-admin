import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IImportOrganizations, ImportOrganizations } from '../import-organizations.model';

import { ImportOrganizationsService } from './import-organizations.service';

describe('ImportOrganizations Service', () => {
  let service: ImportOrganizationsService;
  let httpMock: HttpTestingController;
  let elemDefault: IImportOrganizations;
  let expectedResult: IImportOrganizations | IImportOrganizations[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ImportOrganizationsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      orgMasterId: 'AAAAAAA',
      orgName: 'AAAAAAA',
      partenaire: 'AAAAAAA',
      profile: 'AAAAAAA',
      launchCreationDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          launchCreationDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ImportOrganizations', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          launchCreationDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          launchCreationDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ImportOrganizations()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ImportOrganizations', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orgMasterId: 'BBBBBB',
          orgName: 'BBBBBB',
          partenaire: 'BBBBBB',
          profile: 'BBBBBB',
          launchCreationDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          launchCreationDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ImportOrganizations', () => {
      const patchObject = Object.assign(
        {
          orgMasterId: 'BBBBBB',
          orgName: 'BBBBBB',
          partenaire: 'BBBBBB',
        },
        new ImportOrganizations()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          launchCreationDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ImportOrganizations', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orgMasterId: 'BBBBBB',
          orgName: 'BBBBBB',
          partenaire: 'BBBBBB',
          profile: 'BBBBBB',
          launchCreationDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          launchCreationDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ImportOrganizations', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addImportOrganizationsToCollectionIfMissing', () => {
      it('should add a ImportOrganizations to an empty array', () => {
        const importOrganizations: IImportOrganizations = { id: 123 };
        expectedResult = service.addImportOrganizationsToCollectionIfMissing([], importOrganizations);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(importOrganizations);
      });

      it('should not add a ImportOrganizations to an array that contains it', () => {
        const importOrganizations: IImportOrganizations = { id: 123 };
        const importOrganizationsCollection: IImportOrganizations[] = [
          {
            ...importOrganizations,
          },
          { id: 456 },
        ];
        expectedResult = service.addImportOrganizationsToCollectionIfMissing(importOrganizationsCollection, importOrganizations);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ImportOrganizations to an array that doesn't contain it", () => {
        const importOrganizations: IImportOrganizations = { id: 123 };
        const importOrganizationsCollection: IImportOrganizations[] = [{ id: 456 }];
        expectedResult = service.addImportOrganizationsToCollectionIfMissing(importOrganizationsCollection, importOrganizations);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(importOrganizations);
      });

      it('should add only unique ImportOrganizations to an array', () => {
        const importOrganizationsArray: IImportOrganizations[] = [{ id: 123 }, { id: 456 }, { id: 55488 }];
        const importOrganizationsCollection: IImportOrganizations[] = [{ id: 123 }];
        expectedResult = service.addImportOrganizationsToCollectionIfMissing(importOrganizationsCollection, ...importOrganizationsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const importOrganizations: IImportOrganizations = { id: 123 };
        const importOrganizations2: IImportOrganizations = { id: 456 };
        expectedResult = service.addImportOrganizationsToCollectionIfMissing([], importOrganizations, importOrganizations2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(importOrganizations);
        expect(expectedResult).toContain(importOrganizations2);
      });

      it('should accept null and undefined values', () => {
        const importOrganizations: IImportOrganizations = { id: 123 };
        expectedResult = service.addImportOrganizationsToCollectionIfMissing([], null, importOrganizations, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(importOrganizations);
      });

      it('should return initial array if no ImportOrganizations is added', () => {
        const importOrganizationsCollection: IImportOrganizations[] = [{ id: 123 }];
        expectedResult = service.addImportOrganizationsToCollectionIfMissing(importOrganizationsCollection, undefined, null);
        expect(expectedResult).toEqual(importOrganizationsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
