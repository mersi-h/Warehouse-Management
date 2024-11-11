import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerOrderManagementComponent } from './manager-order-management.component';

describe('ManagerOrderManagementComponent', () => {
  let component: ManagerOrderManagementComponent;
  let fixture: ComponentFixture<ManagerOrderManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerOrderManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerOrderManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
