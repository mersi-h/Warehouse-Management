import { Component, OnInit } from '@angular/core';
import {AdminSettingsService} from "../services/admin-settings.service";
import {MessageService} from "primeng/api";



@Component({
  selector: 'app-app-configuration',
  templateUrl: './app-configuration.component.html',
  styleUrls: ['./app-configuration.component.css']
})
export class AppConfigurationComponent implements OnInit {

  configData: { key: string; value: number }[] = [];
  editingValue: string | null = null;

  constructor(
    private configService: AdminSettingsService,
    private messageService: MessageService
  ) {}


  ngOnInit(): void {
    this.fetchConfigurations();
  }

  fetchConfigurations(): void {
    this.configService.getDeliveryPeriod().subscribe(
      (data) => {
        // Assuming the returned data contains all configurations
        this.configData=[];
        this.configData.push({key: 'MAX_DELIVERY_PERIOD',value:data});
      },
      (error) => {
        console.error('Error fetching configurations', error);
      }
    );
  }

  updateConfiguration(row: { key: string; value: string }): void {
    const newValue = parseInt(row.value, 10);

    if (newValue > 30) {
      this.messageService.add({ severity: 'error', summary: 'Validation Error', detail: 'The period cannot exceed 30 days.' });
      return;
    }

    this.configService.updateDeliveryPeriod(newValue).subscribe(
      (response) => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Configuration updated successfully.' });
        this.fetchConfigurations(); // Refresh data after update
      },
      (error) => {
        console.error('Error updating configuration', error);
        this.messageService.add({ severity: 'error', summary: 'Update Failed', detail: 'Failed to update configuration.' });
      }
    );
  }




}
