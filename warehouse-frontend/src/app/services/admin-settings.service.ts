import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AppConfiguration} from "../models/app-configuration";

const API_URL = 'http://localhost:8082/api/admin/config';

@Injectable({
  providedIn: 'root'
})

export class AdminSettingsService {

  constructor(private http: HttpClient) {
  }

  getDeliveryPeriod(): Observable<number> {
    return this.http.get<number>(API_URL + '/delivery-period');
  }

  updateDeliveryPeriod(period: number): Observable<AppConfiguration> {
    return this.http.put<AppConfiguration>(API_URL + '/delivery-period', period);
  }
}
