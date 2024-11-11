import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {map, Observable, take} from "rxjs";
import {TokenStorageService} from "../services/token-storage.service";

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private authService:  TokenStorageService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot, // Access route-specific data
    state: RouterStateSnapshot
  ): Observable<boolean> {
    const expectedRole = route.data['expectedRole']; // Get the expected role from route data

    return this.authService.userRole$.pipe(
      take(1), // Take the first value emitted
      map((role) => {
        if (role !== expectedRole) {
          // Redirect to access denied page if role doesn't match
          this.router.navigate(['access-denied']);
          return false; // Prevent navigation
        }
        return true; // Allow navigation
      })
    );
  }
}
