import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdditionalKeys, AdditionalKeys } from '../additional-keys.model';

import { AdditionalKeysService } from './additional-keys.service';

describe('AdditionalKeys Service', () => {
  let service: AdditionalKeysService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdditionalKeys;
  let expectedResult: IAdditionalKeys | IAdditionalKeys[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdditionalKeysService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      key: 'AAAAAAA',
      value: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AdditionalKeys', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdditionalKeys()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdditionalKeys', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          value: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdditionalKeys', () => {
      const patchObject = Object.assign(
        {
          key: 'BBBBBB',
          value: 'BBBBBB',
        },
        new AdditionalKeys()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdditionalKeys', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          key: 'BBBBBB',
          value: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a AdditionalKeys', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdditionalKeysToCollectionIfMissing', () => {
      it('should add a AdditionalKeys to an empty array', () => {
        const additionalKeys: IAdditionalKeys = { id: 123 };
        expectedResult = service.addAdditionalKeysToCollectionIfMissing([], additionalKeys);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(additionalKeys);
      });

      it('should not add a AdditionalKeys to an array that contains it', () => {
        const additionalKeys: IAdditionalKeys = { id: 123 };
        const additionalKeysCollection: IAdditionalKeys[] = [
          {
            ...additionalKeys,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdditionalKeysToCollectionIfMissing(additionalKeysCollection, additionalKeys);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdditionalKeys to an array that doesn't contain it", () => {
        const additionalKeys: IAdditionalKeys = { id: 123 };
        const additionalKeysCollection: IAdditionalKeys[] = [{ id: 456 }];
        expectedResult = service.addAdditionalKeysToCollectionIfMissing(additionalKeysCollection, additionalKeys);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(additionalKeys);
      });

      it('should add only unique AdditionalKeys to an array', () => {
        const additionalKeysArray: IAdditionalKeys[] = [{ id: 123 }, { id: 456 }, { id: 60674 }];
        const additionalKeysCollection: IAdditionalKeys[] = [{ id: 123 }];
        expectedResult = service.addAdditionalKeysToCollectionIfMissing(additionalKeysCollection, ...additionalKeysArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const additionalKeys: IAdditionalKeys = { id: 123 };
        const additionalKeys2: IAdditionalKeys = { id: 456 };
        expectedResult = service.addAdditionalKeysToCollectionIfMissing([], additionalKeys, additionalKeys2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(additionalKeys);
        expect(expectedResult).toContain(additionalKeys2);
      });

      it('should accept null and undefined values', () => {
        const additionalKeys: IAdditionalKeys = { id: 123 };
        expectedResult = service.addAdditionalKeysToCollectionIfMissing([], null, additionalKeys, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(additionalKeys);
      });

      it('should return initial array if no AdditionalKeys is added', () => {
        const additionalKeysCollection: IAdditionalKeys[] = [{ id: 123 }];
        expectedResult = service.addAdditionalKeysToCollectionIfMissing(additionalKeysCollection, undefined, null);
        expect(expectedResult).toEqual(additionalKeysCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
