import {Roles} from "./roles";

export class User{
  id?: bigint;
  email!: string;
  name!: string;
  surname!: string;
  password!: string;
  roles!: Roles;


  constructor() {
  }
}
