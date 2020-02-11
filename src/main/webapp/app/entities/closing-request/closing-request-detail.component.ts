import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClosingRequest } from 'app/shared/model/closing-request.model';

@Component({
  selector: 'jhi-closing-request-detail',
  templateUrl: './closing-request-detail.component.html'
})
export class ClosingRequestDetailComponent implements OnInit {
  closingRequest: IClosingRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ closingRequest }) => (this.closingRequest = closingRequest));
  }

  previousState(): void {
    window.history.back();
  }
}
