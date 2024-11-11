import { Component, OnInit } from '@angular/core';
import {Items} from "../models/items";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ConfirmationService, MessageService} from "primeng/api";
import {ItemService} from "../services/item.service";
import {ItemDialogComponent} from "./item-dialog/item-dialog.component";

@Component({
  selector: 'app-items-management',
  templateUrl: './items-management.component.html',
  styleUrls: ['./items-management.component.css']
})
export class ItemsManagementComponent implements OnInit {
  items: Items[] = [];
  ref?: DynamicDialogRef;

  constructor(
    private itemService: ItemService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.loadItems();
  }

  loadItems() {
    this.itemService.getAllItems().subscribe((data) => {
      this.items = data;
    });
  }

  openNewItemDialog() {
    this.ref = this.dialogService.open(ItemDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      data: {isEdit: false}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadItems();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Item added successfully!'});
      }
    });
  }

  openEditItemDialog(item: Items) {
    this.ref = this.dialogService.open(ItemDialogComponent, {
      showHeader: false,
      closable: false,
      contentStyle: {'overflow': 'auto', 'padding': '5'},
      width: '50%',
      data: {item: item, isEdit: true}
    });

    this.ref.onClose.subscribe((result) => {
      if (result) {
        this.loadItems();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Item updated successfully!'});
      }
    });
  }

  deleteItem(itemId: bigint) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this item?',
      accept: () => {
        this.itemService.deleteItemById(itemId).subscribe(() => {
          this.loadItems();
          this.messageService.add({severity: 'success', summary: 'Deleted', detail: 'Item deleted successfully!'});
        });
      }
    });
  }

}
