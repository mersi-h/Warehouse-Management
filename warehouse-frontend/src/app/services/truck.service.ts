import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Truck} from "../models/truck";

const API_URL = 'http://localhost:8082/api/trucks';

@Injectable({
  providedIn: 'root'
})
export class TruckService{


  constructor(private http: HttpClient) {
  }

  getTruckById(truckId: bigint): Observable<Truck> {
    return this.http.get<Truck>(API_URL + '/truck/' + truckId);
  }

  saveTruck(truck: Truck): Observable<Truck> {
    return this.http.post<Truck>(API_URL + '/save', truck);
  }

  editTruck(updatedTruck: Truck): Observable<Truck> {
    return this.http.put<Truck>(API_URL + '/update', updatedTruck);
  }

  getAllTrucks(): Observable<Array<Truck>> {
    return this.http.get<Array<Truck>>(API_URL + '/get-all');
  }

  deleteTruckById(truckId: bigint): Observable<void> {
    return this.http.delete<void>(API_URL + '/delete-truck/' + truckId);
  }


}
