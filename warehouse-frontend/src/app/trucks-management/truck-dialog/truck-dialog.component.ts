import {Component, OnInit} from '@angular/core';
import {Roles} from "../../models/roles";
import {User} from "../../models/user";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {UserService} from "../../services/user.service";
import {Truck} from "../../models/truck";
import {TruckService} from "../../services/truck.service";

@Component({
  selector: 'app-truck-dialog',
  templateUrl: './truck-dialog.component.html',
  styleUrls: ['./truck-dialog.component.css']
})
export class TruckDialogComponent implements OnInit {

  isEdit: boolean = false;
  truck: Truck;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private truckService: TruckService
  ) {
    this.truck = new Truck();
    if (this.config.data.isEdit) {
      this.isEdit = this.config.data.isEdit;
      console.log(this.isEdit)
    }
    if (this.config.data.truck) {
      this.truck = JSON.parse(JSON.stringify(this.config.data.truck)) as Truck;
    }

  }

  saveTruck() {

    if (this.isEdit) {
      this.truckService.editTruck(this.truck).subscribe(() => this.ref.close(true));
    } else {
      this.truckService.saveTruck(this.truck).subscribe(() => this.ref.close(true));
    }
  }

  ngOnInit(): void {
  }

}
