import {Component, OnInit} from '@angular/core';
import {Order} from "../models/order";
import {EnumStatusUtil, Status} from "../models/status";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {OrderService} from "../services/order.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {OrderApprovalDeclined} from "../models/order-approval-declined";
import {DeclineOrderDialogComponent} from "./decline-order-dialog/decline-order-dialog.component";
import {Router} from "@angular/router";
import {ItemDialogComponent} from "../items-management/item-dialog/item-dialog.component";
import {ScheduleDeliveryDialogComponent} from "./schedule-delivery-dialog/schedule-delivery-dialog.component";
import {ScheduleDelivery} from "../models/schedule-delivery";

@Component({
  selector: 'app-manager-order-management',
  templateUrl: './manager-order-management.component.html',
  styleUrls: ['./manager-order-management.component.css']
})
export class ManagerOrderManagementComponent implements OnInit {
  ordersByStatus: Order[] = [];
  statusOptions = Object.keys(Status).map(status => ({label: status, value: Status[status as keyof typeof Status]}));
  selectedStatus?: Status;
  ref?: DynamicDialogRef;
  approvalDeclined: OrderApprovalDeclined = new OrderApprovalDeclined();

  constructor(private readonly orderService: OrderService,
              private dialogService: DialogService,
              private confirmationService: ConfirmationService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadOrders();
  }

  private loadOrders() {
    if (this.selectedStatus) {
      this.orderService.getAllOrders(this.selectedStatus)
        .subscribe(o => this.ordersByStatus = o);
    } else {
      this.orderService.getAllOrders()
        .subscribe(o => this.ordersByStatus = o);
    }
  }

  changeStatus(event: Status) {
    this.selectedStatus = event;
    this.loadOrders();
  }

  getStatus(order: Order) {
    return EnumStatusUtil.getStatusString(order.status);
  }

  viewOrderDetails(orderNumber: bigint) {
    this.router.navigate(['/orders', orderNumber]);
  }

  approveOrder(order: Order) {
    this.approvalDeclined.approve = true;
    this.approvalDeclined.orderId = order.orderNumber;
    this.orderService.approveOrDeclineOrder(this.approvalDeclined).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success', detail: 'Order approved successfully!'});
      this.loadOrders();
    });

  }

  openDeclineDialog(order: Order) {
    this.ref = this.dialogService.open(DeclineOrderDialogComponent, {
      header: 'Decline Order',
      width: '40%',
      data: {order: order}
    });
    this.ref.onClose.subscribe((reason: string) => {
      if (reason) {
        this.declineOrder(order, reason);
      }
    });
  }

  // Decline Order with Reason
  declineOrder(order: Order, reason: string) {
    this.approvalDeclined.approve = false;
    this.approvalDeclined.orderId = order.orderNumber;
    this.approvalDeclined.declineReason = reason;
    this.orderService.approveOrDeclineOrder(this.approvalDeclined).subscribe(() => {
      this.messageService.add({severity: 'info', summary: 'Order Declined', detail: 'Order declined with reason.'});
      this.loadOrders();
    });
  }

  openSheduleDialog(order: Order) {
    this.ref = this.dialogService.open(ScheduleDeliveryDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      height: '70%',
      data: {orderId: order.orderNumber}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadOrders();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Delivery scheduled successfully!'});
      }
    });
  }
}
