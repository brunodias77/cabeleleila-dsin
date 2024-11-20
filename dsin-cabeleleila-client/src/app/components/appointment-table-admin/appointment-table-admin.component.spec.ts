import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentTableAdminComponent } from './appointment-table-admin.component';

describe('AppointmentTableAdminComponent', () => {
  let component: AppointmentTableAdminComponent;
  let fixture: ComponentFixture<AppointmentTableAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppointmentTableAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentTableAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
