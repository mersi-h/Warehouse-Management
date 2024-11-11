export enum Roles {
  CLIENT = 'CLIENT',
  WAREHOUSE_MANAGER = 'WAREHOUSE_MANAGER',
  SYSTEM_ADMIN = 'SYSTEM_ADMIN'
}

export class EnumRolesUtil {
  static getRoleString(role: Roles): string {
    switch (role) {
      case Roles.CLIENT:
        return 'CLIENT';
      case Roles.WAREHOUSE_MANAGER:
        return 'WAREHOUSE_MANAGER';
      case Roles.SYSTEM_ADMIN:
        return 'SYSTEM_ADMIN';
    }
  }
}
