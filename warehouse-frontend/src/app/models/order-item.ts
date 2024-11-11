import {Items} from "./items";
import {Order} from "./order";

export class OrderItem {

  id?: bigint;

  quantity!: number;

  totalPrice!: number;

  item!: Items;

  order!: Order;

}
