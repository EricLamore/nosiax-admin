import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AdminNosiaxSharedModule } from 'app/shared/shared.module';
import { AdminNosiaxCoreModule } from 'app/core/core.module';
import { AdminNosiaxAppRoutingModule } from './app-routing.module';
import { AdminNosiaxHomeModule } from './home/home.module';
import { AdminNosiaxEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AdminNosiaxSharedModule,
    AdminNosiaxCoreModule,
    AdminNosiaxHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AdminNosiaxEntityModule,
    AdminNosiaxAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class AdminNosiaxAppModule {}
