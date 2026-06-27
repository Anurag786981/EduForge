import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassAnalyticsComponent } from './class-analytics.component';

describe('ClassAnalyticsComponent', () => {
  let component: ClassAnalyticsComponent;
  let fixture: ComponentFixture<ClassAnalyticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassAnalyticsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
