import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UserManagementComponent } from './user-management/user-management.component';
import { TrucksManagementComponent } from './trucks-management/trucks-management.component';
import { ItemsManagementComponent } from './items-management/items-management.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {TokenStorageService} from "./services/token-storage.service";
import {UserService} from "./services/user.service";
import {HttpClientModule} from "@angular/common/http";
import { AppConfigurationComponent } from './app-configuration/app-configuration.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import {ButtonModule} from "primeng/button";
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {ToastModule} from "primeng/toast";
import {PasswordModule} from "primeng/password";
import {ConfirmationService, MessageService} from "primeng/api";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import { UserDialogComponent } from './user-management/user-dialog/user-dialog.component';
import {DropdownModule} from "primeng/dropdown";
import {TableModule} from "primeng/table";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogService, DynamicDialogModule} from "primeng/dynamicdialog";
import {MenubarModule} from "primeng/menubar";
import { TopbarComponent } from './topbar/topbar.component';
import {authInterceptorProviders} from "./_helpers/auth.interceptor";
import {InputNumberModule} from "primeng/inputnumber";
import {PanelModule} from "primeng/panel";
import {CommonModule} from "@angular/common";
import { TruckDialogComponent } from './trucks-management/truck-dialog/truck-dialog.component';
import { ItemDialogComponent } from './items-management/item-dialog/item-dialog.component';
import { ClientOrderManagementComponent } from './client-order-management/client-order-management.component';
import { OrderCreateDialogComponent } from './client-order-management/order-create-dialog/order-create-dialog.component';
import { ManagerOrderManagementComponent } from './manager-order-management/manager-order-management.component';
import { DeclineOrderDialogComponent } from './manager-order-management/decline-order-dialog/decline-order-dialog.component';
import { OrderDetailedComponent } from './order-detailed/order-detailed.component';
import { ScheduleDeliveryDialogComponent } from './manager-order-management/schedule-delivery-dialog/schedule-delivery-dialog.component';
import {CalendarModule} from "primeng/calendar";
import {MultiSelectModule} from "primeng/multiselect";
import { ForgotPasswordComponent } from './login/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './login/reset-password/reset-password.component';

@NgModule({
  declarations: [
    AppComponent,
    UserManagementComponent,
    TrucksManagementComponent,
    ItemsManagementComponent,
    LoginComponent,
    HomeComponent,
    AppConfigurationComponent,
    AccessDeniedComponent,
    UserDialogComponent,
    TopbarComponent,
    TruckDialogComponent,
    ItemDialogComponent,
    ClientOrderManagementComponent,
    OrderCreateDialogComponent,
    ManagerOrderManagementComponent,
    DeclineOrderDialogComponent,
    OrderDetailedComponent,
    ScheduleDeliveryDialogComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,

  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule,
    PanelModule,
    CommonModule,
    InputNumberModule,
    PasswordModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    ReactiveFormsModule,
    DropdownModule,
    TableModule,
    ConfirmDialogModule,
    DynamicDialogModule,
    ButtonModule,
    CardModule,
    InputTextModule,
    ToastModule,
    MenubarModule,
    CalendarModule,
    MultiSelectModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    authInterceptorProviders,
    DialogService,
    TokenStorageService,
    UserService,
    MessageService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
