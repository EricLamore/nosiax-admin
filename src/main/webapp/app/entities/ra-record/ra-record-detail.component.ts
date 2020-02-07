import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRaRecord } from 'app/shared/model/ra-record.model';

@Component({
  selector: 'jhi-ra-record-detail',
  templateUrl: './ra-record-detail.component.html'
})
export class RaRecordDetailComponent implements OnInit {
  raRecord: IRaRecord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ raRecord }) => (this.raRecord = raRecord));
  }

  previousState(): void {
    window.history.back();
  }
}
