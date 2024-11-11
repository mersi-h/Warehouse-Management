import {Component} from '@angular/core';
import {User} from "./models/user";
import {TokenStorageService} from "./services/token-storage.service";
import {NavigationEnd, Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'warehouse-frontend';
  isLoggedIn = false;
  loggedIn = false;
  showAdminBoard = false;

  constructor(private tokenStorageService: TokenStorageService,
              private router: Router) {
  }

  ngOnInit(): void {
    TokenStorageService.loggedIn.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
    console.log(this.isLoggedIn)
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const allowedRoutesWithoutLogin = ['/login', '/forgot-password', '/reset-password'];
        const currentRoute = this.router.url.split('?')[0];  // gets the path without query params

        if (!this.isLoggedIn && !allowedRoutesWithoutLogin.includes(currentRoute)) {
          this.router.navigate(['login']);
        }
      }
    });
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate(['login'])
  }
}
