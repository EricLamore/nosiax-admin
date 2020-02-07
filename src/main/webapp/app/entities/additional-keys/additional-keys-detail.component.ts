import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdditionalKeys } from 'app/shared/model/additional-keys.model';

@Component({
  selector: 'jhi-additional-keys-detail',
  templateUrl: './additional-keys-detail.component.html'
})
export class AdditionalKeysDetailComponent implements OnInit {
  additionalKeys: IAdditionalKeys | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ additionalKeys }) => (this.additionalKeys = additionalKeys));
  }

  previousState(): void {
    window.history.back();
  }
}
