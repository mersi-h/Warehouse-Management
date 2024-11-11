import {Component, OnInit} from '@angular/core';
import {Order} from "../models/order";
import {EnumStatusUtil, Status} from "../models/status";
import {OrderService} from "../services/order.service";
import {TokenStorageService} from "../services/token-storage.service";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ConfirmationService, MessageService} from "primeng/api";
import {OrderCreateDialogComponent} from "./order-create-dialog/order-create-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-order-management',
  templateUrl: './client-order-management.component.html',
  styleUrls: ['./client-order-management.component.css']
})
export class ClientOrderManagementComponent implements OnInit {
  ordersByUserIdAndStatus: Order[] = [];
  statusOptions = Object.keys(Status).map(status => ({label: status, value: Status[status as keyof typeof Status]}));
  selectedStatus?: Status;
  ref?: DynamicDialogRef;

  constructor(private readonly orderService: OrderService,
              private readonly token: TokenStorageService,
              private dialogService: DialogService,
              private confirmationService: ConfirmationService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadOrders();
  }

  private loadOrders() {
    let userId = this.token.getUser()?.id;
    if (userId) {
      if (this.selectedStatus) {
        this.orderService.getAllOrdersByUserId(userId, this.selectedStatus)
          .subscribe(o => this.ordersByUserIdAndStatus = o);
      } else {
        this.orderService.getAllOrdersByUserId(userId)
          .subscribe(o => this.ordersByUserIdAndStatus = o);
      }
    }
  }

  openNewOrderDialog() {
    this.ref = this.dialogService.open(OrderCreateDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      data: {isEdit: false}
    });
    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadOrders();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Order added successfully!'});
      }
    });
  }

  openEditOrderDialog(order: Order) {
    this.ref = this.dialogService.open(OrderCreateDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      data: {order: order, isEdit: true}
    });
    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadOrders();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Item updated successfully!'});
      }
    });
  }

  getStatus(order: Order) {
    return EnumStatusUtil.getStatusString(order.status);
  }

  deleteOrder(id: bigint) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this order?',
      accept: () => {
        this.orderService.deleteOrderById(id).subscribe(() => {
          this.loadOrders();
          this.messageService.add({severity: 'success', summary: 'Deleted', detail: 'Order deleted successfully!'});
        });
      }
    });
  }

  submit(order: Order) {
    order.status = Status.AWAITING_APPROVAL;
    order.submittedDate = new Date();
    this.orderService.saveOrder(order).subscribe(o => o);
  }

  changeStatus(event: Status) {
    this.selectedStatus = event;
    this.loadOrders();
  }

  viewOrderDetails(orderNumber: bigint) {
    this.router.navigate(['/orders', orderNumber]);
  }

  cancelOrder(order: any) {
    order.status = Status.CANCELED;
    this.orderService.saveOrder(order).subscribe(o => o);
  }

}
