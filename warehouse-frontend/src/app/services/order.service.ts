import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {OrderItem} from "../models/order-item";
import {Order} from "../models/order";
import {Status} from "../models/status";
import {OrderDetailed} from "../models/order-detailed";
import {OrderApprovalDeclined} from "../models/order-approval-declined";

const API_URL = 'http://localhost:8082/api/orders';

@Injectable({
  providedIn: 'root'
})

export class OrderService {

  constructor(private http: HttpClient) {
  }

  getOrderDetailedById(orderId: bigint): Observable<OrderDetailed> {
    return this.http.get<OrderDetailed>(API_URL + '/get-order-detailed/' + orderId);
  }

  saveOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(API_URL + '/save', order);
  }

  editOrder(updatedOrder: Order): Observable<Order> {
    return this.http.put<Order>(API_URL + '/update', updatedOrder);
  }

  getAllOrders(status?: Status): Observable<Array<Order>> {
    let params: HttpParams = new HttpParams();
    if (status) {
      params = params.append('status', status);
    }
    return this.http.get<Array<Order>>(API_URL + '/get-all', {params});
  }

  getAllOrdersByUserId(userId: bigint, status?: Status): Observable<Array<Order>> {
    let params: HttpParams = new HttpParams().append('userId', userId.toString());
    if (status) {
      params = params.append('status', status);
    }
    return this.http.get<Array<Order>>(API_URL + '/get-by-user', {params});
  }

  deleteOrderById(orderId: bigint): Observable<void> {
    return this.http.delete<void>(API_URL + '/delete-order/' + orderId);
  }

  approveOrDeclineOrder(orderApprovalDeclined: OrderApprovalDeclined): Observable<Order> {
    return this.http.post<Order>(API_URL + '/approve-decline', orderApprovalDeclined);
  }

}
