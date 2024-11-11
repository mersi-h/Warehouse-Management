import { Component, OnInit } from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {Items} from "../../models/items";
import {ItemService} from "../../services/item.service";

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.css']
})
export class ItemDialogComponent implements OnInit {

  isEdit: boolean = false;
  item: Items;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private itemService: ItemService
  ) {
    this.item = new Items();
    if (this.config.data.isEdit) {
      this.isEdit = this.config.data.isEdit;
      console.log(this.isEdit)
    }
    if (this.config.data.item) {
      this.item = JSON.parse(JSON.stringify(this.config.data.item)) as Items;
    }

  }

  saveItem() {

    if (this.isEdit) {
      this.itemService.editItem(this.item).subscribe(() => this.ref.close(true));
    } else {
      this.itemService.saveItem(this.item).subscribe(() => this.ref.close(true));
    }
  }

  ngOnInit(): void {
  }


}
