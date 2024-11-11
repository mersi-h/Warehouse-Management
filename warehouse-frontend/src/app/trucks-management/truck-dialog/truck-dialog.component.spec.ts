import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TruckDialogComponent } from './truck-dialog.component';

describe('TruckDialogComponent', () => {
  let component: TruckDialogComponent;
  let fixture: ComponentFixture<TruckDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TruckDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TruckDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
