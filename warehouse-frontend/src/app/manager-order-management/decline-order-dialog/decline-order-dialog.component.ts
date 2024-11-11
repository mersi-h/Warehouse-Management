import { Component, OnInit } from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-decline-order-dialog',
  templateUrl: './decline-order-dialog.component.html',
  styleUrls: ['./decline-order-dialog.component.css']
})
export class DeclineOrderDialogComponent implements OnInit {

  declineReason: string = '';

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) {}

  submitDeclineReason() {
    this.ref.close(this.declineReason);
  }

  ngOnInit(): void {
  }

}
