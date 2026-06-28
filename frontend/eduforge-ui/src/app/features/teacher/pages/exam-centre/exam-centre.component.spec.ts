import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamCentreComponent } from './exam-centre.component';

describe('ExamCentreComponent', () => {
  let component: ExamCentreComponent;
  let fixture: ComponentFixture<ExamCentreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExamCentreComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExamCentreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
