import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminNosiaxSharedModule } from 'app/shared/shared.module';
import { AdditionalKeysComponent } from './additional-keys.component';
import { AdditionalKeysDetailComponent } from './additional-keys-detail.component';
import { AdditionalKeysUpdateComponent } from './additional-keys-update.component';
import { AdditionalKeysDeleteDialogComponent } from './additional-keys-delete-dialog.component';
import { additionalKeysRoute } from './additional-keys.route';

@NgModule({
  imports: [AdminNosiaxSharedModule, RouterModule.forChild(additionalKeysRoute)],
  declarations: [
    AdditionalKeysComponent,
    AdditionalKeysDetailComponent,
    AdditionalKeysUpdateComponent,
    AdditionalKeysDeleteDialogComponent
  ],
  entryComponents: [AdditionalKeysDeleteDialogComponent]
})
export class AdminNosiaxAdditionalKeysModule {}
