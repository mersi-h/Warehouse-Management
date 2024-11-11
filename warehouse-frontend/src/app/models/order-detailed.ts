import {Order} from "./order";
import {OrderItem} from "./order-item";

export class OrderDetailed {

  orderDto?: Order;

  orderItemDtos?: Array<OrderItem>;
}
