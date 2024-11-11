import {Status} from "./status";
import {User} from "./user";
import {Shipment} from "./shipment";

export class Order {

  orderNumber?: bigint;

  submittedDate?: Date;

  deadlineDate?: Date;

  totalPrice!: number;

  status!: Status;

  user?: User;

  shipment?: Shipment;

  declineReason?: string;

}
