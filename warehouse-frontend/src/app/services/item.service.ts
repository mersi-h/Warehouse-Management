import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {Items} from "../models/items";

const API_URL = 'http://localhost:8082/api/items';

@Injectable({
  providedIn: 'root'
})
export class ItemService{

  constructor(private http: HttpClient) {
  }

  getItemById(itemId: bigint): Observable<Items> {
    return this.http.get<Items>(API_URL + '/item/' + itemId);
  }

  saveItem(item: Items): Observable<Items> {
    return this.http.post<Items>(API_URL + '/save', item);
  }

  editItem(updatedItem: Items): Observable<Items> {
    return this.http.put<Items>(API_URL + '/update', updatedItem);
  }

  getAllItems(): Observable<Array<Items>> {
    return this.http.get<Array<Items>>(API_URL + '/get-all');
  }

  deleteItemById(itemId: bigint): Observable<void> {
    return this.http.delete<void>(API_URL + '/delete-item/' + itemId);
  }
}
