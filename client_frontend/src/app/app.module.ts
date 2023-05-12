import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from 'src/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { View1Component } from './components/view1.component';
import { View0Component } from './components/view0.component'
import { UploadImageService } from './services/upload-image.service';
import { View2Component } from './components/view2.component';

@NgModule({
  declarations: [
    AppComponent,
    View1Component,
    View0Component,
    View2Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [ UploadImageService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
