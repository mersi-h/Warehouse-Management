import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ConfirmationService, MessageService} from "primeng/api";
import {User} from "../models/user";
import {UserDialogComponent} from "./user-dialog/user-dialog.component";
import {LoginUser} from "../models/login-user";
import {EnumRolesUtil, Roles} from "../models/roles";

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  users: User[] = [];
  ref?: DynamicDialogRef;

  constructor(
    private userService: UserService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
    });
  }

  openNewUserDialog() {
    this.ref = this.dialogService.open(UserDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto','padding':'5'},
      width: '50%',
      data: {isEdit: false}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadUsers();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'User added successfully!'});
      }
    });
  }

  openEditUserDialog(user: User) {
    this.ref = this.dialogService.open(UserDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto','padding':'5'},
      width: '50%',
      data: {user: user, isEdit: true}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadUsers();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'User updated successfully!'});
      }
    });
  }

  deleteUser(userId: bigint) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this user?',
      accept: () => {
        this.userService.deleteUserById(userId).subscribe(() => {
          this.loadUsers();
          this.messageService.add({severity: 'success', summary: 'Deleted', detail: 'User deleted successfully!'});
        });
      }
    });
  }

  getRole(user: User): string {
    return EnumRolesUtil.getRoleString(user.roles);
  }
}
