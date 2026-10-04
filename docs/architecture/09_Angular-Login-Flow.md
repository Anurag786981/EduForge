# Login Flow

## Objective

Authenticate the user and securely store JWT Token
for future API calls.

---

## Login Flow

User
    │
    ▼
Login Component
    │
    ▼
AuthService.login(request)
    │
    ▼
Spring Boot Login API
    │
    ▼
Observable<LoginResponse>
    │
    ▼
tap()

Inside tap()

1. Save JWT Token
2. Save Logged-in User

using

TokenService

↓

Browser Local Storage

↓

Response returns to Login Component

↓

subscribe()

↓

Show Success Message

↓

Navigate Dashboard

---

## Why tap()?

tap() is used for side effects.

It allows us to

- Save JWT
- Save User

without modifying the original LoginResponse.

---

## Why TokenService?

TokenService is responsible only for

- Saving Token
- Reading Token
- Removing Token
- Saving User
- Removing User

This follows the Single Responsibility Principle.

---

## Why AuthService?

AuthService contains Authentication Business Logic.

It communicates with Spring Boot.

It does not know Local Storage implementation.

---

## Design Principles

- Separation of Concerns
- Single Responsibility Principle
- Layered Architecture