import { Component, OnInit } from '@angular/core';
import {OrderDetailed} from "../models/order-detailed";
import {ActivatedRoute} from "@angular/router";
import {OrderService} from "../services/order.service";
import { Location } from '@angular/common';


@Component({
  selector: 'app-order-detailed',
  templateUrl: './order-detailed.component.html',
  styleUrls: ['./order-detailed.component.css']
})
export class OrderDetailedComponent implements OnInit {

  orderDetail: OrderDetailed = { orderDto: undefined, orderItemDtos: [] };

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private location: Location
  ) {}

  ngOnInit() {
    const orderId = this.route.snapshot.paramMap.get('orderId');
    if (orderId) {
      this.orderService.getOrderDetailedById(BigInt(orderId)).subscribe(data => {
        this.orderDetail = data;
      });
    }
  }

  goBack() {
    this.location.back();
  }
}
