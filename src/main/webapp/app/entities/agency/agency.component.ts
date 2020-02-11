import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from './agency.service';
import { AgencyDeleteDialogComponent } from './agency-delete-dialog.component';

@Component({
  selector: 'jhi-agency',
  templateUrl: './agency.component.html'
})
export class AgencyComponent implements OnInit, OnDestroy {
  agencies?: IAgency[];
  eventSubscriber?: Subscription;

  constructor(protected agencyService: AgencyService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.agencyService.query().subscribe((res: HttpResponse<IAgency[]>) => (this.agencies = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAgencies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAgency): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAgencies(): void {
    this.eventSubscriber = this.eventManager.subscribe('agencyListModification', () => this.loadAll());
  }

  delete(agency: IAgency): void {
    const modalRef = this.modalService.open(AgencyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agency = agency;
  }
}
