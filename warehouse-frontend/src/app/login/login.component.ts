import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../services/token-storage.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../models/login-request";
import {Roles} from "../models/roles";
import {LoginUser} from "../models/login-user";
import {UserService} from "../services/user.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user?: LoginUser;
  valCheck: string[] = ['remember'];
  logIn: LoginRequest = new LoginRequest();
  password!: string;
  response: any;
  private isLoggedIn: boolean = false;
  private isLoginFailed: boolean = false;
  private role?: Roles;
  loginMessage: string | null = null;

  constructor(
    private readonly loginService: UserService,
    private readonly tokenStorage: TokenStorageService,
    private readonly router: Router,
    private readonly messageService: MessageService) {
  }

  ngOnInit(): void {
    TokenStorageService.loggedIn.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
    if (this.isLoggedIn) {
      this.role = this.tokenStorage.getUser()?.role ?? Roles.CLIENT;

      this.router.navigate(['home']);
    }
  }

  onSignIn() {
    this.loginService.login(this.logIn).subscribe({
      next: (data) => {
        console.log(data);
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.user = data;
        this.role = data.role;
        this.role = this.tokenStorage.getUser()?.role ?? Roles.CLIENT;


        // this.role = this.tokenStorage.getUser().role;
        TokenStorageService.loggedIn.next(this.isLoggedIn);
        this.router.navigate(['/home']);
      },
      // this.response = data;

      error: (err) => {
          this.loginMessage = 'Invalid email or password.';
          this.isLoginFailed = true;

        // this.toastr.error(this.translate.instant("Username-or-password-is-wrong!"));
      }
    })
  }


}
