import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Order} from "../models/order";
import {Observable} from "rxjs";
import {ScheduleDelivery} from "../models/schedule-delivery";

const API_URL = 'http://localhost:8082/api/delivery';

@Injectable({
  providedIn: 'root'
})
export class DeliverySchedulerService{

  constructor(private http: HttpClient) {
  }

  scheduleDelivery(schedulerDelivery: ScheduleDelivery): Observable<Order> {
    return this.http.post<Order>(API_URL + '/schedule', schedulerDelivery);
  }

  getUnavailableDatesForDelivery(orderId: bigint): Observable<Array<Date>> {
    return this.http.get<Array<Date>>(API_URL + '/get-dates/' + orderId);
  }

}
