import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateServicesAdminComponent } from './create-services-admin.component';

describe('CreateServicesAdminComponent', () => {
  let component: CreateServicesAdminComponent;
  let fixture: ComponentFixture<CreateServicesAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateServicesAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateServicesAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
