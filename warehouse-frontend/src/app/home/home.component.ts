import { Component, OnInit } from '@angular/core';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private messageService: MessageService) { }

  startApplication() {
    this.messageService.add({ severity: 'info', summary: 'Welcome', detail: 'Let\'s  get started with managing your warehouse!' });
  }

  ngOnInit(): void {
  }

}
