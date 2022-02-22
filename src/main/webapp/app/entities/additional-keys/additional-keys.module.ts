import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdditionalKeysComponent } from './list/additional-keys.component';
import { AdditionalKeysDetailComponent } from './detail/additional-keys-detail.component';
import { AdditionalKeysUpdateComponent } from './update/additional-keys-update.component';
import { AdditionalKeysDeleteDialogComponent } from './delete/additional-keys-delete-dialog.component';
import { AdditionalKeysRoutingModule } from './route/additional-keys-routing.module';

@NgModule({
  imports: [SharedModule, AdditionalKeysRoutingModule],
  declarations: [
    AdditionalKeysComponent,
    AdditionalKeysDetailComponent,
    AdditionalKeysUpdateComponent,
    AdditionalKeysDeleteDialogComponent,
  ],
  entryComponents: [AdditionalKeysDeleteDialogComponent],
})
export class AdditionalKeysModule {}
