import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';

import {CommonModule} from "@angular/common";

import {LayoutModule} from '@angular/cdk/layout';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';

import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatBadgeModule} from '@angular/material/badge';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTableModule} from '@angular/material/table';

@NgModule({
  imports: [

    BrowserAnimationsModule,
    HttpClientModule,

    LayoutModule,

    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,

    MatSidenavModule,

    MatButtonModule,
    MatTooltipModule,

    MatTableModule,

    CommonModule
  ],
  exports: [

    BrowserAnimationsModule,
    HttpClientModule,

    LayoutModule,

    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatToolbarModule,

    MatCardModule,

    MatButtonModule,
    MatButtonToggleModule,
    MatBadgeModule,

    MatTableModule,

    CommonModule
  ]

})
export class AngularMaterialModule {
}
