import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherDashboardKpisComponent } from './teacher-dashboard-kpis.component';

describe('TeacherDashboardKpisComponent', () => {
  let component: TeacherDashboardKpisComponent;
  let fixture: ComponentFixture<TeacherDashboardKpisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherDashboardKpisComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeacherDashboardKpisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
