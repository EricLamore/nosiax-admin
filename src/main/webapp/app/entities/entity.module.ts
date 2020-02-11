import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ra-record',
        loadChildren: () => import('./ra-record/ra-record.module').then(m => m.AdminNosiaxRaRecordModule)
      },
      {
        path: 'voucher',
        loadChildren: () => import('./voucher/voucher.module').then(m => m.AdminNosiaxVoucherModule)
      },
      {
        path: 'additional-keys',
        loadChildren: () => import('./additional-keys/additional-keys.module').then(m => m.AdminNosiaxAdditionalKeysModule)
      },
      {
        path: 'closing-request',
        loadChildren: () => import('./closing-request/closing-request.module').then(m => m.AdminNosiaxClosingRequestModule)
      },
      {
        path: 'agency',
        loadChildren: () => import('./agency/agency.module').then(m => m.AdminNosiaxAgencyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AdminNosiaxEntityModule {}
