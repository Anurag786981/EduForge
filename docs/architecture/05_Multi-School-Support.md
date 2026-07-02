Why Multi School?

Single Database

Shared Application

School Isolation

school_id

Data Filtering

JWT schoolId

Tenant Security

Future Scaling  




EduForge Cloud
                       │
      ┌────────────────┼────────────────┐
      │                │                │
      ▼                ▼                ▼
School A        School B        School C
ABC Public      XYZ Academy     Bright Kids

      │                │                │
      ▼                ▼                ▼
Teachers        Teachers         Teachers
Students        Students         Students
Parents         Parents          Parents

                    Internet
                        │
                        ▼
                 Angular Frontend
                        │
                        ▼
                 Spring Boot API
                        │
          ┌─────────────┴─────────────┐
          │                           │
          ▼                           ▼
Authentication Service        Business Modules
│
┌───────────────────────────┼──────────────────────────┐
▼                           ▼                          ▼
Student Module             Attendance Module         Question Paper Module
│
▼
MySQL Database

#########################
One Backend

↓

School A

↓

Teachers

↓

Students

-------------------

School B

↓

Teachers

↓

Students