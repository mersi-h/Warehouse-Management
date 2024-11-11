import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user'
import {TokenStorageService} from "./token-storage.service";
import {LoginRequest} from "../models/login-request";
import {LoginUser} from "../models/login-user";

const API_URL = 'http://localhost:8082/api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  constructor(private http: HttpClient,
              private token: TokenStorageService) {

  }

  login(credentials: LoginRequest): Observable<LoginUser> {
    return this.http.post<LoginUser>(API_URL + 'login', credentials);
  }

  getUserById(userId: bigint): Observable<User> {
    return this.http.get<User>(API_URL + 'user/'+userId);
  }

  forgotPassword(email: string): Observable<any> {
    let params: HttpParams = new HttpParams().append('email', email);
    return this.http.get(API_URL + 'forgot-password', { params });
  }
  resetPassword(token: string, newPassword: string): Observable<any> {
    let params: HttpParams = new HttpParams().append('token', token)
      .append('new-pass', newPassword);
    return this.http.get(API_URL + 'reset-password', { params });
  }
  saveUser(user: User): Observable<User> {
    return this.http.post<User>(API_URL + 'add-user', user);
  }
  editUser(updatedUser: User): Observable<User> {
    return this.http.post<User>(API_URL + 'add-user', updatedUser);
  }
  getAllUsers(): Observable<Array<User>> {
    return this.http.get<Array<User>>(API_URL + 'get-all');
  }
  deleteUserById(userId: bigint): Observable<void> {
    return this.http.delete<void>(API_URL + 'delete-user/'+userId);
  }


}
