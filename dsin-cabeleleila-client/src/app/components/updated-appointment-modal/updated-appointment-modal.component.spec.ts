import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatedAppointmentModalComponent } from './updated-appointment-modal.component';

describe('UpdatedAppointmentModalComponent', () => {
  let component: UpdatedAppointmentModalComponent;
  let fixture: ComponentFixture<UpdatedAppointmentModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdatedAppointmentModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdatedAppointmentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
