export enum Status {
  CREATED = 'CREATED',
  AWAITING_APPROVAL = 'AWAITING_APPROVAL',
  APPROVED = 'APPROVED',
  DECLINED = 'DECLINED',
  UNDER_DELIVERY = 'UNDER_DELIVERY',
  FULFILLED = 'FULFILLED',
  CANCELED = 'CANCELED'
}
export class EnumStatusUtil {
  static getStatusString(status: Status): string {
    switch (status) {
      case Status.CREATED:
        return 'CREATED';
      case Status.AWAITING_APPROVAL:
        return 'AWAITING_APPROVAL';
      case Status.APPROVED:
        return 'APPROVED';
      case Status.DECLINED:
        return 'DECLINED';
      case Status.UNDER_DELIVERY:
        return 'UNDER_DELIVERY';
      case Status.FULFILLED:
        return 'FULFILLED';
      case Status.CANCELED:
        return 'CANCELED';
    }
  }
}
