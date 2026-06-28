# EduForge MVP Requirements Summary

## 1. Project Overview

**EduForge** is a cloud-based Coaching Institute Management Platform designed to digitize day-to-day operations for educational institutions. The MVP focuses on core operational needs across four primary user roles with responsive design for desktop, mobile web, and future native apps.

---

## 2. Scope Overview

### 2.1 User Roles & Personas

| Role | Persona | Primary Goals | Key Workflows |
|------|---------|---------------|---------------|
| **Principal** | Dr. Meera Iyer | Operational oversight, financial control, approvals | Daily health check, approval queue, financial overview, staff management |
| **Admin** | Anita Sharma | Records management, admissions, payments | Student admission, ID cards, fee collection, ledger updates |
| **Teacher** | Rahul Menon | Classroom execution, student tracking | Attendance marking, progress notes, homework management, question paper generation |
| **Parent** | Priya Kapoor | Child monitoring, engagement | Progress tracking, homework review, fee payments, pickup confirmation |

### 2.2 MVP Modules

#### 1. **Authentication & Authorization**
- JWT-based authentication
- Role-based access control (RBAC)
- Session management
- Multi-device login/logout
- Password reset and account recovery

#### 2. **Student Management**
- Student registration and onboarding
- Profile management (photos, contact info)
- Batch/class assignment
- Student search and filtering
- Parent linking
- Document upload and storage

#### 3. **Attendance Management**
- Daily attendance marking (teacher-facing)
- Bulk attendance import
- Monthly attendance reports
- Attendance analytics and trends
- Parent notifications for absence
- Attendance-based alerts (low attendance patterns)

#### 4. **Fee Management**
- Fee structure setup
- Fee collection tracking
- Payment history
- Due fee monitoring
- Automated reminders
- Payment gateway integration
- Receipt generation
- Financial reports and reconciliation

#### 5. **Question Bank & Paper Generator**
- Subject-wise question organization
- Chapter-wise categorization
- Difficulty level classification (Easy, Medium, Hard)
- Question tagging and search
- Automated paper generation
- Mark distribution (50%, 30%, 20% for difficulty levels)
- PDF export with formatting
- Paper preview and manual adjustment

#### 6. **Notifications & Communication**
- In-app notifications (dashboard + bell icon)
- Email notifications
- SMS notifications (Future enhancement)
- WhatsApp integration (Future enhancement)
- Parent pickup confirmations
- Fee reminders
- Attendance alerts

#### 7. **Dashboard & Reporting**
- Role-specific dashboards (4 variations)
- KPI metrics and analytics
- Progress tracking and reports
- Financial reports
- Attendance reports
- Exportable reports (PDF, CSV)

#### 8. **Responsive Design**
- Desktop browser support (1024px+)
- Tablet support (768px - 1023px)
- Mobile web support (320px - 767px)
- Progressive Web App (PWA) features
- Touch-optimized interface
- Offline capability

---

## 3. Functional Requirements by Module

### 3.1 Authentication & Authorization

**Functional Requirements**:
- [ ] User login with email/username and password
- [ ] JWT token generation and validation
- [ ] Role-based dashboard routing
- [ ] Session timeout after 30 minutes of inactivity
- [ ] "Remember me" functionality (7-day session)
- [ ] Password reset via email
- [ ] Account lockout after 5 failed login attempts
- [ ] Two-factor authentication (2FA) for admin and principal
- [ ] Logout functionality (clear tokens)
- [ ] Multi-device login tracking

**Non-Functional**:
- [ ] Password strength requirements (min 8 chars, uppercase, lowercase, number, special)
- [ ] HTTPS encryption for all auth endpoints
- [ ] CORS configuration for frontend domain
- [ ] Rate limiting on login endpoints (5 requests per minute)
- [ ] Audit logging for auth events

---

### 3.2 Student Management

**Functional Requirements**:
- [ ] Create new student profile
- [ ] Edit student information (name, DOB, contact, address)
- [ ] Upload student photo (JPEG, PNG, max 2MB)
- [ ] Upload documents (Aadhar, birth certificate, etc.)
- [ ] Assign students to batches/classes
- [ ] Link parents to students
- [ ] Search students by name, roll number, batch
- [ ] Filter students by batch, status (active/inactive)
- [ ] Export student list (CSV, PDF)
- [ ] Bulk student import (CSV)
- [ ] View student profile (read-only for parents)
- [ ] Archive/deactivate student accounts
- [ ] Student status dashboard (active, inactive, graduated)

**Non-Functional**:
- [ ] Support for 10,000+ students in system
- [ ] Search response time < 500ms
- [ ] Image compression and optimization
- [ ] Data validation and sanitization
- [ ] Soft delete for student records

---

### 3.3 Attendance Management

**Functional Requirements**:
- [ ] Teachers mark attendance for their classes
- [ ] Bulk attendance entry (copy from previous day)
- [ ] Attendance import via CSV
- [ ] Monthly attendance report by student
- [ ] Class-level attendance summary
- [ ] Attendance analytics (trends, patterns)
- [ ] Low attendance alerts (< 75%)
- [ ] Parent notifications for absence
- [ ] Attendance approval workflow (optional)
- [ ] Attendance history view
- [ ] Edit attendance (for admin/principal)
- [ ] Generate attendance certificates

**Non-Functional**:
- [ ] Support real-time attendance entry for 500+ students
- [ ] Attendance data persistence and backup
- [ ] Attendance marked after time window restricted to admin
- [ ] Automated attendance reports generation
- [ ] Attendance export in multiple formats

---

### 3.4 Fee Management

**Functional Requirements**:
- [ ] Define fee structure (amount, due date, frequency)
- [ ] Manual fee entry
- [ ] Fee collection tracking
- [ ] Payment history per student
- [ ] Due fee alerts and reminders
- [ ] Receipt generation (printable PDF)
- [ ] Partial payment support
- [ ] Payment gateway integration (Razorpay/PayU)
- [ ] Refund management
- [ ] Fee reconciliation and reporting
- [ ] Fee waivers and discounts
- [ ] Financial dashboard for finance role
- [ ] Fee-based reports (collection, outstanding, aged receivables)

**Non-Functional**:
- [ ] Support for 10,000+ fee records
- [ ] PCI compliance for payment data
- [ ] Automated fee invoice generation
- [ ] Email receipts to parents
- [ ] Reminders 7, 3, 1 days before due date
- [ ] Monthly reconciliation reports

---

### 3.5 Question Bank & Paper Generator

**Functional Requirements**:
- [ ] Create and categorize questions (subject, chapter, difficulty)
- [ ] Store questions with multiple formats (MCQ, short answer, long answer)
- [ ] Add explanation/solution to questions
- [ ] Tag questions with topics and keywords
- [ ] Search questions by subject, chapter, difficulty, keyword
- [ ] Rate difficulty levels (Easy, Medium, Hard)
- [ ] Create paper templates
- [ ] Auto-generate papers with specified:
  - Total marks (e.g., 40, 50, 100)
  - Time duration
  - Question distribution by difficulty
  - Question distribution by chapter
- [ ] Manual paper editing (add/remove questions)
- [ ] Paper preview with formatting
- [ ] PDF export with proper formatting
- [ ] Print functionality
- [ ] Save paper templates for reuse
- [ ] Share papers with students

**Non-Functional**:
- [ ] Support 5,000+ questions in bank
- [ ] Fast search performance (< 500ms)
- [ ] Paper generation < 2 seconds
- [ ] PDF quality and formatting
- [ ] Question duplication prevention
- [ ] Question version control

---

### 3.6 Dashboard & Reporting

**Functional Requirements**:

**Principal Dashboard**:
- [ ] School health score (KPI)
- [ ] Student attendance summary
- [ ] Staff attendance summary
- [ ] Fee collection progress (vs. target)
- [ ] Pending approvals count
- [ ] Recent school alerts
- [ ] Financial overview
- [ ] Teacher attendance and leave
- [ ] Quick access to approval queue

**Admin Dashboard**:
- [ ] Open admin tasks count
- [ ] New admissions count
- [ ] Fee reminders sent count
- [ ] Pending payment requests
- [ ] ID cards ready count
- [ ] Admission verification queue
- [ ] Pickup confirmation audit status
- [ ] Fee ledger summary
- [ ] Recent admin alerts

**Teacher Dashboard**:
- [ ] Students present (today)
- [ ] Class timetable (today/week)
- [ ] Homework pending
- [ ] Parent comments/concerns
- [ ] Question bank access (quick link)
- [ ] Recent student progress updates
- [ ] Attendance status (for marking)
- [ ] Paper generation quick access

**Parent Dashboard**:
- [ ] Child attendance (current month %)
- [ ] Subject-wise progress scores
- [ ] Homework assignments and status
- [ ] Fee payment status
- [ ] Pickup confirmation status
- [ ] School activity media
- [ ] Recent teacher feedback
- [ ] Upcoming events/holidays

**General Reporting**:
- [ ] Attendance reports (monthly, student-wise)
- [ ] Financial reports (collection, outstanding, aged receivables)
- [ ] Student progress reports
- [ ] Fee collection trend charts
- [ ] Export reports (PDF, CSV, Excel)
- [ ] Scheduled automated reports
- [ ] Custom report builder

**Non-Functional**:
- [ ] Dashboard load time < 2 seconds
- [ ] Real-time metric updates
- [ ] Historical data for trends (12 months)
- [ ] Report generation < 10 seconds
- [ ] Mobile-optimized charts
- [ ] Charts responsive across devices

---

### 3.7 Notifications & Communication

**Functional Requirements**:
- [ ] In-app notification bell (with unread count)
- [ ] Notification history and archival
- [ ] Email notifications with templates
- [ ] SMS notifications (optional MVP)
- [ ] Push notifications for PWA
- [ ] Parent pickup confirmations
- [ ] Fee payment reminders (7, 3, 1 days)
- [ ] Attendance absence alerts
- [ ] Teacher communication to parents
- [ ] Parent communication portal
- [ ] Bulk notifications (admin feature)
- [ ] Notification preferences per user
- [ ] Do not disturb settings

**Non-Functional**:
- [ ] Email delivery within 5 minutes
- [ ] SMS delivery within 2 minutes
- [ ] Push notification delivery within 10 seconds
- [ ] Notification persistence and retry
- [ ] Audit trail for all notifications
- [ ] Unsubscribe functionality for emails

---

### 3.8 Responsive Design

**Functional Requirements**:
- [ ] Desktop view (1024px+) with sidebar navigation
- [ ] Tablet view (768px-1023px) with drawer navigation
- [ ] Mobile view (320px-767px) with bottom navigation
- [ ] Touch-optimized buttons (48px minimum)
- [ ] Responsive navigation drawer
- [ ] Hamburger menu for mobile
- [ ] PWA installation prompts
- [ ] Offline capability with service workers
- [ ] Responsive tables (card-based on mobile)
- [ ] Responsive charts and graphs
- [ ] Adaptive images and lazy loading
- [ ] Keyboard navigation support
- [ ] Screen reader compatibility
- [ ] Color contrast compliance (WCAG AA)

**Non-Functional**:
- [ ] Core Web Vitals pass (LCP < 2.5s, FID < 100ms, CLS < 0.1)
- [ ] Mobile performance > 75 (Lighthouse)
- [ ] Desktop performance > 85 (Lighthouse)
- [ ] Page load time < 3s on 4G
- [ ] 90% score on accessibility audit
- [ ] Touch response time < 100ms

---

## 4. Non-Functional Requirements

### 4.1 Performance
- [ ] API response time < 200ms (95th percentile)
- [ ] Dashboard load time < 2 seconds
- [ ] Page transition animation 60fps
- [ ] Bundle size < 500KB (gzipped)
- [ ] Database query optimization for reports

### 4.2 Security
- [ ] End-to-end encryption for sensitive data
- [ ] HTTPS/TLS for all communications
- [ ] SQL injection prevention (parameterized queries)
- [ ] XSS protection
- [ ] CSRF tokens for form submissions
- [ ] Role-based access control (RBAC)
- [ ] Field-level data masking (PII)
- [ ] Audit logging for all operations
- [ ] Data anonymization for analytics

### 4.3 Reliability & Availability
- [ ] 99.5% uptime SLA
- [ ] Database backup every 6 hours
- [ ] Disaster recovery plan (RPO < 1 hour)
- [ ] Graceful error handling
- [ ] Retry logic for failed operations
- [ ] Circuit breaker for external APIs

### 4.4 Scalability
- [ ] Support 50+ concurrent users
- [ ] Support 10,000+ student records
- [ ] Support 100,000+ attendance records
- [ ] Support 50,000+ fee transactions
- [ ] Database indexing for performance
- [ ] Horizontal scalability (stateless design)

### 4.5 Usability
- [ ] Intuitive UI/UX (role-specific)
- [ ] Keyboard navigation support
- [ ] Screen reader compatibility
- [ ] Touch-friendly interface
- [ ] Responsive design (desktop, tablet, mobile)
- [ ] Accessibility WCAG 2.1 AA compliant
- [ ] Consistent branding and styling

### 4.6 Maintainability
- [ ] Modular code architecture
- [ ] Comprehensive code documentation
- [ ] Unit test coverage > 70%
- [ ] Integration test coverage
- [ ] E2E test coverage for critical flows
- [ ] API documentation (Swagger)
- [ ] Deployment automation (CI/CD)

---

## 5. Technical Stack

### Frontend
- **Framework**: Angular 19
- **UI Components**: Angular Material
- **HTTP Client**: HttpClientModule
- **State Management**: RxJS (Reactive programming)
- **Build Tool**: Angular CLI
- **Testing**: Jasmine + Karma

### Backend
- **Framework**: Spring Boot 3.5.15
- **Language**: Java 17
- **Database**: MySQL 8
- **Security**: Spring Security + JWT
- **API Docs**: SpringDoc OpenAPI (Swagger)
- **ORM**: Hibernate + Spring Data JPA
- **PDF Generation**: OpenPDF

### Infrastructure
- **Web Server**: Tomcat (embedded in Spring Boot)
- **Deployment**: Docker containers (optional)
- **CI/CD**: GitHub Actions / GitLab CI (recommended)
- **Hosting**: Cloud platform (AWS / GCP / Azure)

---

## 6. Data Models (Core Entities)

### User / Auth
```
User {
  id: UUID
  email: String (unique)
  password: String (hashed)
  firstName: String
  lastName: String
  phone: String
  role: Role (PRINCIPAL, ADMIN, TEACHER, PARENT)
  profilePhoto: URL
  status: ACTIVE | INACTIVE
  lastLogin: DateTime
  createdAt: DateTime
  updatedAt: DateTime
}
```

### Student
```
Student {
  id: UUID
  name: String
  dateOfBirth: Date
  rollNumber: String
  batch: Batch (foreign key)
  email: String
  phone: String
  address: String
  photo: URL
  documents: Document[]
  parents: Parent[]
  status: ACTIVE | INACTIVE | GRADUATED
  createdAt: DateTime
  updatedAt: DateTime
}
```

### Attendance
```
Attendance {
  id: UUID
  student: Student (foreign key)
  batch: Batch (foreign key)
  date: Date
  status: PRESENT | ABSENT | LEAVE
  markedBy: User (teacher)
  createdAt: DateTime
  updatedAt: DateTime
}
```

### Fee
```
Fee {
  id: UUID
  student: Student (foreign key)
  amount: Decimal
  dueDate: Date
  description: String
  status: UNPAID | PARTIAL | PAID
  payments: Payment[]
  discounts: Decimal (optional)
  createdAt: DateTime
  updatedAt: DateTime
}

Payment {
  id: UUID
  fee: Fee (foreign key)
  amount: Decimal
  paymentDate: Date
  method: CASH | CARD | ONLINE
  reference: String
  receipt: URL
  createdAt: DateTime
}
```

### Question
```
Question {
  id: UUID
  subject: String
  chapter: String
  text: String
  questionType: MCQ | SHORT_ANSWER | LONG_ANSWER
  options: String[] (for MCQ)
  correctAnswer: String
  explanation: String
  marks: Integer
  difficulty: EASY | MEDIUM | HARD
  tags: String[]
  createdBy: User
  createdAt: DateTime
  updatedAt: DateTime
}
```

### Paper
```
Paper {
  id: UUID
  title: String
  subject: String
  totalMarks: Integer
  duration: Integer (minutes)
  questions: Question[]
  difficulty: { easy: %, medium: %, hard: % }
  createdBy: User
  template: Boolean
  createdAt: DateTime
  pdfUrl: URL
}
```

### Notification
```
Notification {
  id: UUID
  recipient: User
  type: EMAIL | SMS | IN_APP | PUSH
  title: String
  message: String
  data: JSON
  read: Boolean
  createdAt: DateTime
  sentAt: DateTime
}
```

---

## 7. API Endpoints (High-Level)

### Authentication
```
POST /api/auth/login
POST /api/auth/logout
POST /api/auth/refresh-token
POST /api/auth/reset-password
POST /api/auth/verify-2fa
```

### Students
```
GET /api/students
POST /api/students
GET /api/students/{id}
PUT /api/students/{id}
DELETE /api/students/{id}
POST /api/students/bulk-import
GET /api/students/batch/{batchId}
```

### Attendance
```
POST /api/attendance
GET /api/attendance/{studentId}
GET /api/attendance/batch/{batchId}/date/{date}
PUT /api/attendance/{id}
GET /api/attendance/report/{studentId}
```

### Fees
```
GET /api/fees
POST /api/fees
GET /api/fees/{studentId}
PUT /api/fees/{id}
POST /api/payments
GET /api/fees/report/outstanding
GET /api/fees/report/collection
```

### Questions
```
GET /api/questions
POST /api/questions
GET /api/questions/{id}
PUT /api/questions/{id}
DELETE /api/questions/{id}
GET /api/questions/search?subject=...&chapter=...&difficulty=...
```

### Papers
```
GET /api/papers
POST /api/papers (create and auto-generate)
GET /api/papers/{id}
PUT /api/papers/{id}
DELETE /api/papers/{id}
POST /api/papers/{id}/generate-pdf
POST /api/papers/{id}/share
```

### Dashboards
```
GET /api/dashboard/principal
GET /api/dashboard/admin
GET /api/dashboard/teacher
GET /api/dashboard/parent/{studentId}
```

### Notifications
```
GET /api/notifications
POST /api/notifications
GET /api/notifications/{id}
PUT /api/notifications/{id}/read
GET /api/notifications/unread-count
```

---

## 8. Implementation Phases

### Phase 0: Infrastructure Setup (Week 1-2)
- [ ] Project structure setup (Angular + Spring Boot)
- [ ] Database schema design and creation
- [ ] Development environment setup
- [ ] Git repository and CI/CD pipeline
- [ ] Design system and component library

### Phase 1: Authentication (Week 3-4)
- [ ] User registration and login
- [ ] JWT token generation and validation
- [ ] Role-based routing
- [ ] Logout and session management
- [ ] Password reset functionality

### Phase 2: Core Dashboards (Week 5-8)
- [ ] Responsive layout framework
- [ ] Dashboard components (metric cards, charts, panels)
- [ ] Role-specific dashboards (all 4 roles)
- [ ] Navigation system (desktop, tablet, mobile)
- [ ] Theme and styling

### Phase 3: Student Management (Week 9-10)
- [ ] Student CRUD operations
- [ ] Bulk import functionality
- [ ] Student profile management
- [ ] Search and filtering

### Phase 4: Attendance Management (Week 11-12)
- [ ] Attendance marking interface
- [ ] Attendance reports
- [ ] Low attendance alerts
- [ ] Parent notifications

### Phase 5: Fee Management (Week 13-14)
- [ ] Fee setup and tracking
- [ ] Payment processing
- [ ] Fee reports
- [ ] Reminders

### Phase 6: Question Bank & Paper Generator (Week 15-16)
- [ ] Question creation and categorization
- [ ] Automated paper generation
- [ ] PDF export
- [ ] Teacher interface

### Phase 7: Notifications & Communication (Week 17-18)
- [ ] In-app notifications
- [ ] Email notifications
- [ ] SMS notifications (optional)
- [ ] Push notifications (PWA)

### Phase 8: Testing & Optimization (Week 19-20)
- [ ] Unit and integration tests
- [ ] E2E testing
- [ ] Performance optimization
- [ ] Accessibility audit
- [ ] Security testing

### Phase 9: Deployment & Documentation (Week 21-22)
- [ ] Production deployment
- [ ] API documentation
- [ ] User documentation
- [ ] Training materials

---

## 9. Success Metrics

### User Adoption
- [ ] 80% of teachers using daily attendance marking within 2 weeks
- [ ] 60% of parents accessing child progress within 1 month
- [ ] 95% of admin staff using system for day-to-day tasks within 2 weeks

### System Performance
- [ ] Dashboard load time < 2 seconds (90th percentile)
- [ ] API response time < 200ms (95th percentile)
- [ ] 99.5% uptime in first 3 months
- [ ] Zero data loss incidents

### Quality Metrics
- [ ] Bug escape rate < 0.5% (QA tested vs. production bugs)
- [ ] Code coverage > 70% for critical paths
- [ ] Accessibility score > 85/100
- [ ] Mobile performance score > 75/100

### Business Metrics
- [ ] Reduce manual record-keeping time by 70%
- [ ] Improve fee collection efficiency by 40%
- [ ] Reduce attendance errors by 90%
- [ ] Achieve 4.5+ star rating from pilot institute

---

## 10. Risk Assessment

### Technical Risks
| Risk | Impact | Probability | Mitigation |
|------|--------|-------------|-----------|
| Database performance degradation | HIGH | MEDIUM | Indexing, query optimization, load testing |
| Mobile responsiveness issues | MEDIUM | MEDIUM | Early mobile testing, responsive framework |
| PDF generation delays | MEDIUM | LOW | Asynchronous processing, queue system |
| API rate limiting | MEDIUM | MEDIUM | Caching, request batching, throttling |

### Operational Risks
| Risk | Impact | Probability | Mitigation |
|------|--------|-------------|-----------|
| Insufficient user training | HIGH | MEDIUM | Documentation, video tutorials, support |
| Data migration issues | HIGH | LOW | Data validation, backup strategy |
| School calendar conflicts | MEDIUM | MEDIUM | Flexible configuration |
| Parent confusion (UI/UX) | MEDIUM | MEDIUM | Simplified parent dashboard, support |

### Security Risks
| Risk | Impact | Probability | Mitigation |
|------|--------|-------------|-----------|
| Data breach | CRITICAL | LOW | Encryption, HTTPS, audit logging |
| Unauthorized access | HIGH | LOW | RBAC, JWT validation |
| Payment fraud | HIGH | LOW | PCI compliance, 3D secure |

---

## 11. Out of Scope (Post-MVP)

- [ ] AI-powered question generation
- [ ] Advanced analytics and ML models
- [ ] Biometric attendance (face recognition)
- [ ] Video conferencing for remote classes
- [ ] E-library and LMS features
- [ ] Mobile app development (Phase 2)
- [ ] WhatsApp integration (Phase 2)
- [ ] Advanced reporting and BI dashboards (Phase 2)
- [ ] Integration with other education platforms
- [ ] Inventory management
- [ ] HR management module

---

**Document Version**: 1.0  
**Last Updated**: June 2026  
**Status**: Approved for Development  
**Project Lead**: Gourav Mishra, Anurag Tiwari
