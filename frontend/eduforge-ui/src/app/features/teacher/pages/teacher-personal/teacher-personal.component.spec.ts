import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherPersonalComponent } from './teacher-personal.component';

describe('TeacherPersonalComponent', () => {
  let component: TeacherPersonalComponent;
  let fixture: ComponentFixture<TeacherPersonalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherPersonalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeacherPersonalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
