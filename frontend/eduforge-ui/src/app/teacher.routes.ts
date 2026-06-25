import { Routes } from '@angular/router';
import { TodayComponent,
    TeacherDashboardHomeComponent,
    ClassActivitiesComponent,
    ClassAttendanceComponent,
    ClassAnalyticsComponent,
    TeacherTimetableComponent,
    TeacherPersonalComponent,
    ClassAssignmentsComponent,
    ExamCentreComponent
 } from './features/teacher/pages';

export const teacherChildRoutes: Routes = [  
      {
        path: '',
        redirectTo : 'home',
        pathMatch: 'full'
      },

      {
        path: 'home',
        component: TeacherDashboardHomeComponent
      },

      {
        path: 'today',
        component: TodayComponent
      },

      {
        path: 'attendance',
        component: ClassAttendanceComponent
      },

      {
        path: 'exams',
        component: ExamCentreComponent
      },
      
      {
        path: 'classanalytics',
        component: ClassAnalyticsComponent
      },
      
      {
        path: 'classactivities',
        component: ClassActivitiesComponent
      },
      
      {
        path: 'timetable',
        component: TeacherTimetableComponent
      },
      
      {
        path: 'personal',
        component: TeacherPersonalComponent
      },

      {
        path: 'assignments',
        component: ClassAssignmentsComponent
      }     
      
    ]