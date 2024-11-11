import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {User} from "../models/user";
import {LoginUser} from "../models/login-user";
import {Roles} from "../models/roles";
import {Router} from "@angular/router";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor(private router: Router) {
    TokenStorageService.loggedIn.next(!!sessionStorage.getItem(TOKEN_KEY));
  }
  private userRoleSubject = new BehaviorSubject<Roles | null>(null);
  userRole$ = this.userRoleSubject.asObservable();

  public static loggedIn: Subject<any> = new Subject;

  signOut(): void {
    this.userRoleSubject.next(null);
    window.sessionStorage.clear();
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.removeItem(USER_KEY);
    TokenStorageService.loggedIn.next(!!sessionStorage.getItem(TOKEN_KEY));
    this.router.navigate(['login'])
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): any {
    return sessionStorage.getItem(TOKEN_KEY);

  }


  public saveUser(user: LoginUser): void {
    this.userRoleSubject.next(user.role ? user.role : null);
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser():LoginUser | null {
    const user = sessionStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
    }
}
