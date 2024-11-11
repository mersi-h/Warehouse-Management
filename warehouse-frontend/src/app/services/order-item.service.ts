import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {OrderItem} from "../models/order-item";

const API_URL = 'http://localhost:8082/api/order-items';

@Injectable({
  providedIn: 'root'
})
export class OrderItemService{

  constructor(private http: HttpClient) {
  }

  getOrderItemById(orderItemId: bigint): Observable<OrderItem> {
    return this.http.get<OrderItem>(API_URL + '/order-item/' + orderItemId);
  }

  getOrderItemsByOrderId(orderId: bigint): Observable<Array<OrderItem>> {
    return this.http.get<Array<OrderItem>>(API_URL + '/get-by-order/' + orderId);
  }

  saveOrderItem(orderItem: OrderItem): Observable<OrderItem> {
    return this.http.post<OrderItem>(API_URL + '/save', orderItem);
  }

  editOrderItem(updatedOrderItem: OrderItem): Observable<OrderItem> {
    return this.http.put<OrderItem>(API_URL + '/update', updatedOrderItem);
  }

  getAllOrderItems(): Observable<Array<OrderItem>> {
    return this.http.get<Array<OrderItem>>(API_URL + '/get-all');
  }

  deleteOrderItemById(itemId: bigint): Observable<void> {
    return this.http.delete<void>(API_URL + '/' + itemId);
  }

}
