import { Component, OnInit } from '@angular/core';
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ConfirmationService, MessageService} from "primeng/api";
import {Truck} from "../models/truck";
import {TruckService} from "../services/truck.service";
import {TruckDialogComponent} from "./truck-dialog/truck-dialog.component";

@Component({
  selector: 'app-trucks-management',
  templateUrl: './trucks-management.component.html',
  styleUrls: ['./trucks-management.component.css']
})
export class TrucksManagementComponent implements OnInit {
  trucks: Truck[] = [];
  ref?: DynamicDialogRef;

  constructor(
    private truckService: TruckService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.loadTrucks();
  }

  loadTrucks() {
    this.truckService.getAllTrucks().subscribe((data) => {
      this.trucks = data;
    });
  }

  openNewTruckDialog() {
    this.ref = this.dialogService.open(TruckDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      data: {isEdit: false}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadTrucks();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Truck added successfully!'});
      }
    });
  }

  openEditTruckDialog(truck: Truck) {
    this.ref = this.dialogService.open(TruckDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      data: {truck: truck, isEdit: true}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadTrucks();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Truck updated successfully!'});
      }
    });
  }

  deleteTruck(userId: bigint) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this truck?',
      accept: () => {
        this.truckService.deleteTruckById(userId).subscribe(() => {
          this.loadTrucks();
          this.messageService.add({severity: 'success', summary: 'Deleted', detail: 'Truck deleted successfully!'});
        });
      }
    });
  }


}
