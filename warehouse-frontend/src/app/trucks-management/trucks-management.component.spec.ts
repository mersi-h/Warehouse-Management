import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrucksManagementComponent } from './trucks-management.component';

describe('TrucksManagementComponent', () => {
  let component: TrucksManagementComponent;
  let fixture: ComponentFixture<TrucksManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrucksManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrucksManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
