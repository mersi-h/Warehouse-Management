import {Roles} from "./roles";

export class LoginUser {
  id!: bigint;
  email!: string;
  fullName!: string;
  role!: Roles;
  token!: string;
}
