# EduForge Responsive Implementation Guide

## 1. CSS Architecture & Organization

### 1.1 SCSS Variables File (variables.scss)

```scss
// Color Palette
$color-bg-primary: #f8fafc;
$color-surface-white: #ffffff;
$color-text-primary: #17202a;
$color-text-secondary: #596578;
$color-text-tertiary: #687384;
$color-border: #dce2ea;

// Role-based Colors (CSS Variables for dynamic theming)
$principal-primary: #2762d8;
$principal-soft: #eaf1ff;
$principal-dark: #173f92;

$admin-primary: #0f8d7a;
$admin-soft: #e8f7f4;
$admin-dark: #096757;

$teacher-primary: #b45a13;
$teacher-soft: #fff1e4;
$teacher-dark: #87400d;

$parent-primary: #9a3fb0;
$parent-soft: #f7eafd;
$parent-dark: #713083;

// Semantic Colors
$color-success: #16a34a;
$color-warning: #f59e0b;
$color-error: #ef4444;
$color-info: #3b82f6;

// Spacing Scale (8px base)
$space-xs: 4px;
$space-sm: 8px;
$space-md: 12px;
$space-lg: 16px;
$space-xl: 20px;
$space-2xl: 24px;
$space-3xl: 32px;
$space-4xl: 40px;

// Border Radius
$radius-sm: 6px;
$radius-md: 8px;
$radius-full: 999px;

// Typography
$font-family-sans: 'Segoe UI', Arial, sans-serif;
$font-size-h1: 27px;
$font-size-h2: 25px;
$font-size-h3: 19px;
$font-size-h4: 15px;
$font-size-body: 13px;
$font-size-label: 12px;
$font-size-caption: 11px;

$font-weight-normal: 650;
$font-weight-bold: 800;
$font-weight-extrabold: 850;

// Shadows
$shadow-subtle: 0 1px 2px rgba(24, 37, 56, 0.08);
$shadow-light: 0 4px 12px rgba(24, 37, 56, 0.12);
$shadow-medium: 0 18px 42px rgba(24, 37, 56, 0.12);

// Z-index scale
$z-hidden: -1;
$z-base: 0;
$z-dropdown: 100;
$z-sticky: 200;
$z-fixed: 300;
$z-modal-backdrop: 400;
$z-modal: 500;
$z-notification: 600;
```

### 1.2 Breakpoints File (breakpoints.scss)

```scss
// Breakpoint definitions
$breakpoint-sm: 480px;
$breakpoint-md: 768px;
$breakpoint-lg: 1024px;
$breakpoint-xl: 1200px;
$breakpoint-2xl: 1400px;

// Mobile-first media queries
@mixin media-sm {
  @media (min-width: $breakpoint-sm) {
    @content;
  }
}

@mixin media-md {
  @media (min-width: $breakpoint-md) {
    @content;
  }
}

@mixin media-lg {
  @media (min-width: $breakpoint-lg) {
    @content;
  }
}

@mixin media-xl {
  @media (min-width: $breakpoint-xl) {
    @content;
  }
}

@mixin media-2xl {
  @media (min-width: $breakpoint-2xl) {
    @content;
  }
}

// Desktop-first media queries (for max-width)
@mixin media-max-md {
  @media (max-width: $breakpoint-md - 1px) {
    @content;
  }
}

@mixin media-max-lg {
  @media (max-width: $breakpoint-lg - 1px) {
    @content;
  }
}
```

### 1.3 Responsive Mixins File (responsive-mixins.scss)

```scss
// Flexbox utilities
@mixin flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}

@mixin flex-between {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

// Grid utilities
@mixin grid-auto-fit($min-size) {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax($min-size, 1fr));
  gap: $space-lg;
}

// Truncate text
@mixin truncate {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

@mixin truncate-lines($lines) {
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: $lines;
  -webkit-box-orient: vertical;
}

// Aspect ratio
@mixin aspect-ratio($width, $height) {
  aspect-ratio: #{$width} / #{$height};
}

// Container queries (for component-level responsiveness)
@mixin container-sm {
  container-type: inline-size;
  @container (min-width: 400px) {
    @content;
  }
}

// Responsive font size
@mixin responsive-font-size($mobile, $tablet, $desktop) {
  font-size: $mobile;
  @include media-md {
    font-size: $tablet;
  }
  @include media-lg {
    font-size: $desktop;
  }
}

// Touch target size
@mixin touch-target($size: 48px) {
  min-width: $size;
  min-height: $size;
  display: flex;
  align-items: center;
  justify-content: center;
}
```

### 1.4 Global Responsive Styles (responsive.scss)

```scss
// Mobile-first approach

// == DESKTOP LAYOUTS (1024px+) ==
.app-container {
  display: grid;
  grid-template-columns: 188px 1fr;
  gap: 0;
  min-height: 100vh;
}

.app-sidebar {
  position: fixed;
  left: 0;
  top: 0;
  width: 188px;
  height: 100vh;
  background: $principal-primary;
  color: white;
  padding: $space-lg;
  overflow-y: auto;
  z-index: $z-sticky;

  @include media-max-lg {
    width: 60px;
    padding: $space-md;

    .sidebar-text {
      display: none;
    }
  }
}

.app-main {
  margin-left: 188px;
  padding: $space-xl;

  @include media-max-lg {
    margin-left: 60px;
  }

  @include media-max-md {
    margin-left: 0;
    padding: $space-md;
    padding-bottom: 70px; // Account for bottom nav
  }
}

// Responsive Header
.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: $space-md;
  margin-bottom: $space-xl;

  @include media-max-md {
    flex-wrap: wrap;
  }
}

.dashboard-title {
  h3 {
    margin: 0 0 $space-sm 0;
    @include responsive-font-size(24px, 25px, 27px);
  }

  p {
    margin: $space-xs 0 0 0;
    font-size: $font-size-label;
    color: $color-text-secondary;
  }
}

// Metric Cards Grid
.metrics-grid {
  display: grid;
  gap: $space-lg;

  // Desktop: 4 columns
  grid-template-columns: repeat(4, 1fr);

  // Tablet: 2 columns
  @include media-max-lg {
    grid-template-columns: repeat(2, 1fr);
  }

  // Mobile: 1-2 columns
  @include media-max-md {
    grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  }
}

// Hero Card
.hero-card {
  display: grid;
  grid-template-columns: 190px 1fr;
  gap: $space-lg;
  align-items: stretch;
  background: $color-surface-white;
  border-radius: $radius-md;
  border: 1px solid $color-border;
  padding: $space-lg;

  // Tablet/Mobile: Stack vertically
  @include media-max-md {
    grid-template-columns: 1fr;
    padding: $space-md;
  }
}

// Bottom Panel Grid
.panel-grid {
  display: grid;
  grid-template-columns: 1.35fr 0.65fr;
  gap: $space-lg;

  // Tablet: Single column
  @include media-max-lg {
    grid-template-columns: 1fr;
  }

  // Mobile: Single column
  @include media-max-md {
    grid-template-columns: 1fr;
  }
}

// Panels
.panel {
  background: $color-surface-white;
  border: 1px solid $color-border;
  border-radius: $radius-md;
  overflow: hidden;
  display: flex;
  flex-direction: column;

  .panel-header {
    padding: $space-lg;
    border-bottom: 1px solid $color-border;
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: $space-md;

    @include media-max-md {
      padding: $space-md;
    }

    h4 {
      margin: 0;
      font-size: $font-size-h4;
      font-weight: $font-weight-bold;
    }
  }

  .panel-body {
    flex: 1;
    padding: $space-lg;
    overflow-y: auto;

    @include media-max-md {
      padding: $space-md;
    }
  }
}

// Tables - Responsive
.data-table {
  width: 100%;
  border-collapse: collapse;

  th, td {
    text-align: left;
    padding: $space-md;
    border-bottom: 1px solid $color-border;

    @include media-max-md {
      padding: $space-sm;
      font-size: 12px;
    }
  }

  th {
    background: #f8faf6;
    font-weight: $font-weight-bold;
    font-size: $font-size-label;
    text-transform: uppercase;
  }

  // Mobile: Card-based layout
  @include media-max-md {
    display: block;

    thead {
      display: none;
    }

    tbody {
      display: block;
    }

    tr {
      display: block;
      border: 1px solid $color-border;
      border-radius: $radius-md;
      margin-bottom: $space-md;
      padding: $space-md;
    }

    td {
      display: flex;
      justify-content: space-between;
      padding: $space-sm 0;
      border: none;

      &::before {
        content: attr(data-label);
        font-weight: $font-weight-bold;
        margin-right: $space-md;
      }
    }
  }
}

// Mobile Bottom Navigation
.bottom-nav {
  display: none;

  @include media-max-md {
    display: flex;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 70px;
    background: $color-surface-white;
    border-top: 1px solid $color-border;
    gap: 0;
    z-index: $z-sticky;

    .nav-item {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: 4px;
      cursor: pointer;
      transition: background 0.2s;

      &:hover {
        background: rgba(0, 0, 0, 0.05);
      }

      .nav-icon {
        font-size: 20px;
      }

      .nav-label {
        font-size: 10px;
        text-align: center;
      }
    }
  }
}
```


## 2. Angular Component Implementation Patterns

### 2.1 Responsive Metric Card Component

```typescript
// metric-card.component.ts
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-metric-card',
  template: `
    <article class="metric-card">
      <div class="metric-label">{{ label }}</div>
      <div class="metric-value">{{ value }}</div>
      <div class="metric-sublabel">{{ sublabel }}</div>
    </article>
  `,
  styleUrls: ['./metric-card.component.scss']
})
export class MetricCardComponent {
  @Input() label: string;
  @Input() value: string | number;
  @Input() sublabel: string;
}
```

```scss
// metric-card.component.scss
.metric-card {
  border: 1px solid $color-border;
  border-radius: $radius-md;
  padding: $space-lg;
  background: $color-surface-white;
  min-width: 0; // Prevents text overflow

  .metric-label {
    color: $color-text-tertiary;
    font-size: $font-size-label;
    font-weight: $font-weight-bold;
    text-transform: uppercase;
    margin-bottom: $space-sm;
  }

  .metric-value {
    color: $color-text-primary;
    font-size: $font-size-h2;
    font-weight: $font-weight-extrabold;
    line-height: 1;
    margin-bottom: $space-md;
  }

  .metric-sublabel {
    color: $color-text-secondary;
    font-size: $font-size-caption;
    line-height: 1.3;
  }
}
```

### 2.2 Responsive Data Table Component

```typescript
// data-table.component.ts
import { Component, Input } from '@angular/core';

interface TableColumn {
  key: string;
  label: string;
  width?: string;
  sortable?: boolean;
}

@Component({
  selector: 'app-data-table',
  template: `
    <div class="table-wrapper">
      <table class="data-table" [class.mobile-view]="isMobileView">
        <thead>
          <tr>
            <th *ngFor="let col of columns">{{ col.label }}</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let row of data">
            <td *ngFor="let col of columns" [attr.data-label]="col.label">
              {{ row[col.key] }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `,
  styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent {
  @Input() columns: TableColumn[];
  @Input() data: any[];

  isMobileView = window.innerWidth < 768;

  constructor() {
    window.addEventListener('resize', () => {
      this.isMobileView = window.innerWidth < 768;
    });
  }
}
```

```scss
// data-table.component.scss
.table-wrapper {
  overflow-x: auto;

  @include media-max-md {
    overflow-x: visible;
  }
}

.data-table {
  width: 100%;
  border-collapse: collapse;

  th, td {
    text-align: left;
    padding: $space-md;
    border-bottom: 1px solid $color-border;
  }

  th {
    background: #f8faf6;
    font-weight: $font-weight-bold;
    font-size: $font-size-label;
  }

  // Mobile card-based layout
  @include media-max-md {
    display: block;

    thead {
      display: none;
    }

    tr {
      display: block;
      border: 1px solid $color-border;
      border-radius: $radius-md;
      margin-bottom: $space-md;
      padding: $space-md;
    }

    td {
      display: flex;
      justify-content: space-between;
      padding: $space-sm 0;
      border: none;
      padding-left: 50%;
      position: relative;

      &::before {
        content: attr(data-label);
        position: absolute;
        left: 0;
        font-weight: $font-weight-bold;
      }
    }
  }
}
```

### 2.3 Responsive Dashboard Layout Component

```typescript
// dashboard-layout.component.ts
import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-dashboard-layout',
  template: `
    <div class="dashboard-container">
      <app-sidebar [isCollapsed]="isTabletView" *ngIf="!isMobileView"></app-sidebar>
      
      <div class="dashboard-content">
        <app-header [showMenu]="isMobileView" (menuClick)="toggleMobileMenu()"></app-header>
        
        <main class="dashboard-main">
          <ng-content></ng-content>
        </main>
      </div>

      <!-- Mobile bottom navigation -->
      <app-bottom-nav *ngIf="isMobileView"></app-bottom-nav>

      <!-- Mobile menu drawer -->
      <app-mobile-drawer [isOpen]="showMobileMenu" (close)="showMobileMenu = false"></app-mobile-drawer>
    </div>
  `,
  styleUrls: ['./dashboard-layout.component.scss']
})
export class DashboardLayoutComponent implements OnInit {
  isMobileView = false;
  isTabletView = false;
  showMobileMenu = false;

  constructor(private breakpointObserver: BreakpointObserver) {}

  ngOnInit() {
    this.breakpointObserver.observe([
      Breakpoints.Small,
      Breakpoints.Medium,
      Breakpoints.Large,
      Breakpoints.XLarge
    ]).subscribe(result => {
      this.isMobileView = result.breakpoints[Breakpoints.Small];
      this.isTabletView = result.breakpoints[Breakpoints.Medium];
    });
  }

  toggleMobileMenu() {
    this.showMobileMenu = !this.showMobileMenu;
  }
}
```

```scss
// dashboard-layout.component.scss
.dashboard-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;

  @include media-lg {
    display: grid;
    grid-template-columns: 188px 1fr;
    grid-template-rows: 1fr;
  }
}

.dashboard-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  @include media-lg {
    grid-column: 2;
  }
}

.dashboard-main {
  flex: 1;
  overflow-y: auto;
  padding: $space-md;

  @include media-md {
    padding: $space-lg;
  }

  @include media-lg {
    padding: $space-xl;
  }
}
```

### 2.4 Responsive Navigation Component

```typescript
// responsive-nav.component.ts
import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';

@Component({
  selector: 'app-responsive-nav',
  template: `
    <!-- Desktop Sidebar -->
    <nav class="nav-desktop" *ngIf="!isMobileView">
      <div class="nav-brand">
        <span class="nav-mark">E</span>
        <span class="nav-text">EduForge</span>
      </div>

      <ul class="nav-menu">
        <li *ngFor="let item of navItems">
          <a [routerLink]="item.path" routerLinkActive="active">
            <span class="nav-icon">{{ item.icon }}</span>
            <span class="nav-label">{{ item.label }}</span>
          </a>
        </li>
      </ul>

      <div class="nav-user">
        <img [src]="userProfile.avatar" class="user-avatar" />
        <div class="user-info">
          <strong>{{ userProfile.name }}</strong>
          <small>{{ userProfile.role }}</small>
        </div>
      </div>
    </nav>

    <!-- Mobile Bottom Navigation -->
    <nav class="nav-mobile" *ngIf="isMobileView">
      <button *ngFor="let item of navItems" 
              [routerLink]="item.path"
              routerLinkActive="active"
              class="nav-item">
        <span class="nav-icon">{{ item.icon }}</span>
        <span class="nav-label">{{ item.label }}</span>
      </button>
    </nav>
  `,
  styleUrls: ['./responsive-nav.component.scss']
})
export class ResponsiveNavComponent implements OnInit {
  @Input() navItems: any[] = [];
  @Input() userProfile: any = {};
  
  isMobileView = false;

  constructor(private breakpointObserver: BreakpointObserver) {}

  ngOnInit() {
    this.breakpointObserver.observe('(max-width: 767px)').subscribe(result => {
      this.isMobileView = result.matches;
    });
  }
}
```

```scss
// responsive-nav.component.scss
.nav-desktop {
  display: none;
  flex-direction: column;
  width: 188px;
  height: 100vh;
  background: $principal-primary;
  color: white;
  padding: $space-lg;
  position: fixed;
  left: 0;
  top: 0;
  z-index: $z-sticky;

  @include media-lg {
    display: flex;
  }

  .nav-brand {
    display: flex;
    align-items: center;
    gap: $space-md;
    margin-bottom: $space-xl;
    font-weight: $font-weight-extrabold;
    font-size: 17px;

    .nav-mark {
      width: 30px;
      height: 30px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: rgba(255, 255, 255, 0.2);
      border-radius: $radius-sm;
    }
  }

  .nav-menu {
    flex: 1;
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    flex-direction: column;
    gap: $space-sm;

    li a {
      display: flex;
      align-items: center;
      gap: $space-md;
      padding: $space-md;
      border-radius: $radius-sm;
      color: rgba(255, 255, 255, 0.7);
      text-decoration: none;
      transition: all 0.2s;

      &:hover, &.active {
        background: rgba(255, 255, 255, 0.15);
        color: white;
      }
    }
  }

  .nav-user {
    display: flex;
    align-items: center;
    gap: $space-md;
    padding: $space-md;
    border-radius: $radius-md;
    background: rgba(255, 255, 255, 0.08);

    .user-avatar {
      width: 32px;
      height: 32px;
      border-radius: 50%;
    }

    .user-info {
      display: flex;
      flex-direction: column;
      gap: 2px;

      strong {
        font-size: $font-size-body;
      }

      small {
        font-size: $font-size-caption;
        opacity: 0.8;
      }
    }
  }
}

.nav-mobile {
  display: flex;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: $color-surface-white;
  border-top: 1px solid $color-border;
  gap: 0;
  z-index: $z-sticky;

  @include media-lg {
    display: none;
  }

  .nav-item {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 4px;
    background: none;
    border: none;
    cursor: pointer;
    color: $color-text-secondary;
    transition: all 0.2s;

    &:hover, &.active {
      background: rgba(0, 0, 0, 0.05);
      color: $principal-primary;
    }

    .nav-icon {
      font-size: 20px;
    }

    .nav-label {
      font-size: 10px;
      text-align: center;
    }
  }
}
```

## 3. CSS Grid Patterns for Responsive Layouts

```scss
// Common grid patterns

// 4-column desktop, 2-column tablet, 1 column mobile
.grid-4-2-1 {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: $space-lg;

  @include media-md {
    grid-template-columns: repeat(2, 1fr);
  }

  @include media-lg {
    grid-template-columns: repeat(4, 1fr);
  }
}

// 3-column desktop, 2-column tablet, 1 column mobile
.grid-3-2-1 {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: $space-lg;

  @include media-md {
    grid-template-columns: repeat(2, 1fr);
  }

  @include media-lg {
    grid-template-columns: repeat(3, 1fr);
  }
}

// 2-column desktop, 1 column mobile (common for panels)
.grid-2-1 {
  display: grid;
  grid-template-columns: 1fr;
  gap: $space-lg;

  @include media-lg {
    grid-template-columns: 1.35fr 0.65fr;
  }
}
```

## 4. Touch-Friendly Component Specifications

```scss
// Minimum touch target sizes
.button-primary {
  min-height: 48px;
  min-width: 48px;
  padding: $space-md $space-lg;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: $space-sm;

  @include media-max-md {
    min-height: 44px;
    min-width: 44px;
  }
}

// Form input touch targets
input, textarea, select {
  min-height: 48px;
  padding: $space-md;
  font-size: 16px; // Prevents zoom on iOS

  @include media-max-md {
    min-height: 44px;
  }
}

// Link touch targets
a {
  @include touch-target(48px);

  @include media-max-md {
    @include touch-target(44px);
  }
}
```

## 5. Testing Responsive Design

```typescript
// dashboard-layout.component.spec.ts
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardLayoutComponent } from './dashboard-layout.component';
import { BreakpointObserver } from '@angular/cdk/layout';

describe('DashboardLayoutComponent - Responsive', () => {
  let component: DashboardLayoutComponent;
  let fixture: ComponentFixture<DashboardLayoutComponent>;
  let breakpointObserver: BreakpointObserver;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardLayoutComponent],
      providers: [BreakpointObserver]
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardLayoutComponent);
    component = fixture.componentInstance;
    breakpointObserver = TestBed.inject(BreakpointObserver);
  });

  it('should show mobile nav on small screens', () => {
    // Mock mobile breakpoint
    spyOn(breakpointObserver, 'observe').and.returnValue({
      subscribe: (callback) => {
        callback({ matches: true, breakpoints: { '(max-width: 767px)': true } });
        return { unsubscribe: () => {} };
      }
    } as any);

    fixture.detectChanges();
    expect(component.isMobileView).toBe(true);
  });

  it('should show desktop nav on large screens', () => {
    // Mock desktop breakpoint
    spyOn(breakpointObserver, 'observe').and.returnValue({
      subscribe: (callback) => {
        callback({ matches: false, breakpoints: { '(max-width: 767px)': false } });
        return { unsubscribe: () => {} };
      }
    } as any);

    fixture.detectChanges();
    expect(component.isMobileView).toBe(false);
  });
});
```

---

**Document Version**: 1.0  
**Status**: Ready for Implementation
