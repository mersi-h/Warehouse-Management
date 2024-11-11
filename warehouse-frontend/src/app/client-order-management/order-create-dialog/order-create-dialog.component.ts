import {Component, OnInit} from '@angular/core';
import {Items} from "../../models/items";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {OrderService} from "../../services/order.service";
import {ItemService} from "../../services/item.service";
import {OrderItem} from "../../models/order-item";
import {OrderItemService} from "../../services/order-item.service";
import {Order} from "../../models/order";
import {Status} from "../../models/status";
import {TokenStorageService} from "../../services/token-storage.service";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-order-create-dialog',
  templateUrl: './order-create-dialog.component.html',
  styleUrls: ['./order-create-dialog.component.css']
})
export class OrderCreateDialogComponent implements OnInit {
  allItems: Items[] = [];
  selectedItem?: Items;

  isEdit: boolean = false;
  order: Order;
  quantity: number = 1;
  orderItems: OrderItem[] = [];
  userId?: bigint;
  user?: User;

  constructor(
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private orderService: OrderService,
    private itemService: ItemService,
    private orderItemService: OrderItemService,
    private token: TokenStorageService,
    private userService: UserService
  ) {
    this.order = new Order();
    if (this.config.data.isEdit) {
      this.isEdit = this.config.data.isEdit;
      console.log(this.isEdit)
    }
    if (this.config.data.order) {
      this.order = JSON.parse(JSON.stringify(this.config.data.order)) as Order;
    }
  }

  ngOnInit() {

    this.userId = this.token.getUser()?.id;
    if (this.userId) {
      this.userService.getUserById(this.userId).subscribe(u => this.user = u);
    }

    this.loadAvailableItems();

    if (this.isEdit) {
      this.initializeOrderForEditing();
    }
  }

  loadAvailableItems() {
    this.itemService.getAllItems().subscribe(items => (this.allItems = items));
  }

  initializeOrderForEditing() {

    let orderId = this.order.orderNumber;
    console.log(orderId)
    if (orderId) {
      this.orderItemService.getOrderItemsByOrderId(orderId).subscribe(o => {
          this.orderItems = o;
          console.log(o);
        }
      );
    }
  }

  addItem() {
    if (this.selectedItem && this.quantity > 0) {
      let orderItem: OrderItem = new OrderItem();
      orderItem.item = this.selectedItem;
      orderItem.quantity = this.quantity;
      orderItem.totalPrice = this.selectedItem.unitPrice * this.quantity;
      if(this.isEdit){
        orderItem.order=this.order;
      }
      this.orderItems.push(orderItem);
      this.selectedItem = undefined;
      this.quantity = 1;
    }
  }

  removeItem(item: OrderItem) {
    this.orderItems = this.orderItems.filter(i => i !== item);
    if (item.id) {
      this.orderItemService.deleteOrderItemById(item.id).subscribe();
    }
  }
  saveOrder() {
    const totalOrderPrice = this.orderItems.reduce((sum, item) => sum + item.totalPrice!, 0);

    if (this.isEdit) {
      this.order.totalPrice = totalOrderPrice;
      this.orderService.editOrder(this.order).subscribe(() => {
        this.saveOrderItems();
      });
    } else {
      this.order.status = Status.CREATED;
      this.order.totalPrice = totalOrderPrice;
      this.order.user = this.user;

      this.orderService.saveOrder(this.order).subscribe(savedOrder => {
        this.orderItems.forEach(item => item.order = savedOrder);
        this.saveOrderItems();
      });
    }
  }

  saveOrderItems() {
    const saveRequests = this.orderItems.map(item => this.orderItemService.saveOrderItem(item));
    Promise.all(saveRequests.map(req => req.toPromise())).then(() => {
      this.ref.close(true);
    });
  }
  closeDialog() {
    this.ref.close();
  }

  modifyItemQuantity(item: OrderItem, newQuantity: number) {
    if (newQuantity > 0) {
      item.quantity = newQuantity;
      item.totalPrice = item.item!.unitPrice * newQuantity;
    }
  }
}
