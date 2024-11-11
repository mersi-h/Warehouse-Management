import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {Roles} from "../models/roles";
import {TokenStorageService} from "../services/token-storage.service";

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

  items: MenuItem[] = [];

  constructor(private authService: TokenStorageService) {
  }

  ngOnInit(): void {
    // Subscribe to the user's role to set up the menu
    this.authService.userRole$.subscribe(role => {
      this.items = this.getMenuItems(role ?? Roles.CLIENT);
    });
  }

  getMenuItems(role: Roles): MenuItem[] {
    console.log(role)

    const menuItems: MenuItem[] = [
      {label: 'Home', icon: 'pi pi-home', routerLink: ['/']},
      {label: 'Logout', icon: 'pi pi-sign-out', command: () => this.authService.signOut()}
    ];

    if (role === Roles.SYSTEM_ADMIN) {
      menuItems.splice(1, 0,
        {label: 'User Management', icon: 'pi pi-users', routerLink: ['/user-management']},
        {label: 'App Config', icon: 'pi pi-cog', routerLink: ['/app-config']}
      );
    } else if (role === Roles.WAREHOUSE_MANAGER) {
      menuItems.splice(1, 0,
        {label: 'Inventory', icon: 'pi pi-box', routerLink: ['/items']},
        {label: 'Order', icon: 'pi pi-receipt', routerLink: ['/manager-orders']},
        {label: 'Truck', icon: 'pi pi-truck', routerLink: ['/trucks']}
      );
    } else if (role === Roles.CLIENT) {
      menuItems.splice(1, 0,
        {label: 'Order', icon: 'pi pi-receipt', routerLink: ['/client-orders']});
    }

    return menuItems;
  }

}
