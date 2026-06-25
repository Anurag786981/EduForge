import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassActivitiesComponent } from './class-activities.component';

describe('ClassActivitiesComponent', () => {
  let component: ClassActivitiesComponent;
  let fixture: ComponentFixture<ClassActivitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassActivitiesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassActivitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
