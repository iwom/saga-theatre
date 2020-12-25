
import {BrowserModule} from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlexModule} from "@angular/flex-layout";

import {ApiProvider} from "./services/api.provider";

import {AngularMaterialModule} from './modules/material.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TheatreService} from "./services/theatre.service";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    AngularMaterialModule,
    FlexModule,
    BrowserAnimationsModule
  ],
  providers: [ApiProvider, TheatreService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
