import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeclineOrderDialogComponent } from './decline-order-dialog.component';

describe('DeclineOrderDialogComponent', () => {
  let component: DeclineOrderDialogComponent;
  let fixture: ComponentFixture<DeclineOrderDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeclineOrderDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeclineOrderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
