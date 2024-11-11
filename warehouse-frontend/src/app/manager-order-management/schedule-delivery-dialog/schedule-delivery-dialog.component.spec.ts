import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleDeliveryDialogComponent } from './schedule-delivery-dialog.component';

describe('ScheduleDeliveryDialogComponent', () => {
  let component: ScheduleDeliveryDialogComponent;
  let fixture: ComponentFixture<ScheduleDeliveryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleDeliveryDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleDeliveryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
