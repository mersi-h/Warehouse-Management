import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {UserService} from "../../services/user.service";
import {Roles} from "../../models/roles";
import {User} from "../../models/user";

@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.css']
})
export class UserDialogComponent implements OnInit {

  roleOptions = Object.keys(Roles).map(role => ({label: role, value: Roles[role as keyof typeof Roles]}));

  isEdit: boolean = false;
  user: User;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private userService: UserService
  ) {
    this.user = new User();
    if(this.config.data.isEdit){
      this.isEdit = this.config.data.isEdit;
      console.log(this.isEdit)
    }
    if (this.config.data.user) {
      this.user = JSON.parse(JSON.stringify(this.config.data.user))as User;
    }

  }

  saveUser() {

    if (this.isEdit) {
      this.userService.editUser(this.user).subscribe(() => this.ref.close(true));
    } else {
      this.userService.saveUser(this.user).subscribe(() => this.ref.close(true));
    }
  }

  ngOnInit(): void {
  }

}
