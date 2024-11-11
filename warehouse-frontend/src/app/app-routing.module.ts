import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserManagementComponent} from "./user-management/user-management.component";
import {RoleGuard} from "./guard/role.guard";
import {Roles} from "./models/roles";
import {AppConfigurationComponent} from "./app-configuration/app-configuration.component";
import {TrucksManagementComponent} from "./trucks-management/trucks-management.component";
import {AccessDeniedComponent} from "./access-denied/access-denied.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {LoginGuard} from "./guard/login.guard";
import {ItemsManagementComponent} from "./items-management/items-management.component";
import {ClientOrderManagementComponent} from "./client-order-management/client-order-management.component";
import {ManagerOrderManagementComponent} from "./manager-order-management/manager-order-management.component";
import {OrderDetailedComponent} from "./order-detailed/order-detailed.component";
import {ForgotPasswordComponent} from "./login/forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./login/reset-password/reset-password.component";

const routes: Routes = [
  {
    path: 'user-management',
    component: UserManagementComponent,
    canActivate: [RoleGuard, LoginGuard],
    data: { expectedRole: Roles.SYSTEM_ADMIN },
  },
  {
    path: 'app-config',
    component: AppConfigurationComponent,
    canActivate: [RoleGuard, LoginGuard],
    data: { expectedRole: Roles.SYSTEM_ADMIN },
  },
  {
    path: 'trucks',
    component: TrucksManagementComponent,
    canActivate: [RoleGuard, LoginGuard],
    data: { expectedRole: Roles.WAREHOUSE_MANAGER },
  },
  {
    path: 'items',
    component: ItemsManagementComponent,
    canActivate: [RoleGuard, LoginGuard],
    data: { expectedRole: Roles.WAREHOUSE_MANAGER},
  },
  {
    path: 'client-orders',
    component: ClientOrderManagementComponent,
    canActivate: [RoleGuard, LoginGuard],
    data: { expectedRole: Roles.CLIENT},
  },
  {
    path: 'manager-orders',
    component: ManagerOrderManagementComponent,
    canActivate: [RoleGuard, LoginGuard],
    data: { expectedRole: Roles.WAREHOUSE_MANAGER},
  },
  {
    path: 'orders/:orderId',
    component: OrderDetailedComponent,
    canActivate: [LoginGuard],
  },
  { path: 'home', component: HomeComponent, canActivate: [LoginGuard]},
  { path: 'access-denied', component: AccessDeniedComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },


  { path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: '**', redirectTo: 'login'},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {

}
