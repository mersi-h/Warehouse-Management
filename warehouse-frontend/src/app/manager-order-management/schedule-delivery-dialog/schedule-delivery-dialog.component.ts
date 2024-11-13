import {Component, OnInit} from '@angular/core';
import {Truck} from "../../models/truck";
import {TruckService} from "../../services/truck.service";
import {OrderService} from "../../services/order.service";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {ScheduleDelivery} from "../../models/schedule-delivery";
import {DeliverySchedulerService} from "../../services/delivery-scheduler.service";
import {AdminSettingsService} from "../../services/admin-settings.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-schedule-delivery-dialog',
  templateUrl: './schedule-delivery-dialog.component.html',
  styleUrls: ['./schedule-delivery-dialog.component.css']
})
export class ScheduleDeliveryDialogComponent implements OnInit {
  selectedDate!: Date;
  unavailableDates: Date[] = [];
  selectedTrucks: Truck[] = [];
  availableTrucks: Truck[] = [];
  orderId?: bigint;
  today: Date;
  maxDate: Date = new Date();

  constructor(private truckService: TruckService,
              private deliveryScheduler: DeliverySchedulerService,
              public ref: DynamicDialogRef,
              private config: DynamicDialogConfig,
              private adminSettingsService: AdminSettingsService,
              private messageService: MessageService) {
    if (this.config.data.orderId) {
      this.orderId = this.config.data.orderId;
    }
    this.today = new Date();

  }

  ngOnInit(): void {
    if (this.orderId) {
      this.deliveryScheduler.getUnavailableDatesForDelivery(this.orderId).subscribe(unavailable =>
        this.unavailableDates = unavailable.map(date => {
          return date instanceof Date ? date : new Date(date);
        }));
    }
    this.truckService.getAllTrucks().subscribe(t => this.availableTrucks = t);
    this.adminSettingsService.getDeliveryPeriod().subscribe(days => {
      this.maxDate = new Date(this.today);
      this.maxDate.setDate(this.today.getDate() + days);
      console.log(this.maxDate)
      return this.maxDate;
    });

  }

  scheduleDelivery() {
    let schedulerDelivery: ScheduleDelivery = new ScheduleDelivery();
    schedulerDelivery.orderId = this.orderId;
    schedulerDelivery.truckIds = this.selectedTrucks.filter(t => t.id !== undefined).map(t => t.id as bigint);
    schedulerDelivery.scheduledDate = this.selectedDate;
    this.deliveryScheduler.scheduleDelivery(schedulerDelivery).subscribe(delivery => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Scheduled delivery successfully.'});

        this.ref.close(delivery);
      },
      error => {
        this.messageService.add({severity: 'error', summary: 'Validation Error', detail: error.error.error});

      }
    )
  }


}
