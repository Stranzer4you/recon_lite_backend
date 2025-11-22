# Recon Lite — Mini Automated Transaction Reconciliation System

## 📌 Overview

Recon Lite is a lightweight **mini transaction reconciliation system**** built using **Spring Boot (Backend)**, **React + TypeScript (Frontend)**, and **PostgreSQL**. This application provides an end-to-end workflow for uploading, managing, and reconciling financial transactions coming from two different sources — **BANK** and **SYSTEM**.

The application implements:

* Transaction storage and retrieval
* Configurable rule-based reconciliation
* Reconciliation history tracking
* Frontend UI with pagination, filters, and manual reconciliation trigger
* Backend APIs deployed on Render
* Frontend deployed on Netlify

This document explains the **modules**, **architecture**, and **features**.

---

# 🚀 Architecture

```
React UI (Netlify)
     |
     | REST API
     v
Spring Boot Backend (Render)
     |
     | JDBC → PostgreSQL
     v
PostgreSQL Cloud DB
```

---

# 📦 Modules

## 1. **Transactions Module**

Handles all operations related to financial transactions.

### Features

* Add a new transaction
* Edit/Delete existing transactions
* Fetch paginated transactions
* Filter by source, status, amount range, and date range
* Return formatted date (`DD/MM/YYYY`)

### API Endpoints

* `GET /transactions` – Paginated fetch
* `POST /transactions` – Create
* `DELETE /transactions/{id}` – Delete

### Database Table

```
Transactions
-------------------------------------------------------
ID (PK) BIGSERIAL
Description TEXT
Amount NUMERIC(12,2)
Source ENUM('BANK', 'SYSTEM')
Status ENUM('RAW','MATCHED','UNMATCHED')
CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

---

## 2. **Rules Module**

Defines rules used during the reconciliation process.

### Available Rule Types

* `MATCH_BY_DATE_AMOUNT`
* `MATCH_BY_AMOUNT`
* Future expansion possible

### Fields

* RuleName
* RuleType
* Priority
* Description
* IsActive

### APIs

* `POST /rules` – Create a rule
* `GET /rules` – Fetch all rules

---

## 3. **Reconciliation Module**

Core of the application — matches BANK and SYSTEM transactions.

### What happens during reconciliation?

1. Fetch **RAW** transactions from both sources
2. Apply active rules in **priority order**
3. If a rule matches → Mark both as **MATCHED**
4. If no rule matches → Mark relevant items as **UNMATCHED**
5. Count:

    * Matched
    * Unmatched
    * Raw
6. Insert these values into `ReconciliationHistory`

### API

```
POST /transactions/reconcile
```

No request body. Auto-applies all rules.

### Key Outcomes

* Clean separation of business logic
* Industry-standard reconciliation pipeline
* Future-ready for additional rule types

---

## 4. **Reconciliation History Module**

Stores the summary of each reconciliation run.

### Table

```
ReconciliationHistory
---------------------------------------------------------
ID BIGSERIAL PRIMARY KEY
MatchedCount INT
UnmatchedCount INT
RawCount INT
CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

### Purpose

* Provides audit trail
* Helps in compliance
* Useful for analytics dashboards later

---

# 🎨 Frontend (React + TypeScript)

The UI is clean, responsive, and minimal.

### Major Features

* Pagination (10 records per page)
* Filters for search
* Add/Delete dialog modals
* Snackbar notifications
* Reconcile button with **loading spinner animation** until API responds
* Highlighted Reconcile button for primary user action

### Reconciliation Button Behavior

* Clicking triggers the POST API
* Sync icon rotates until response is received
* Shows success or error toast

---

# ⚙️ Deployment

## Backend (Render)

* Auto sleep after 15 minutes on free tier
* Public URL: `<backend-url>/recon-lite/...`
* Health Check: `/actuator/health`

## Frontend (Netlify)

* Build using Vite/CRA
* Environment variables stored in Netlify dashboard
* Deployed URL: `<your-netlify-url>`

---

# 🧪 Development Setup

## Backend

```
mvn clean install
mvn spring-boot:run
```

Environment Variables:

```
DB_HOST=
DB_PORT=
DB_USER=
DB_PASSWORD=
```

## Frontend

```
npm install
npm run dev
```

Environment Variables:

```
VITE_API_BASE_URL=
```

---

# 🧱 Folder Structure (Backend)

```
src/main/java/.../controller
src/main/java/.../service
src/main/java/.../repository
src/main/java/.../model
src/main/java/.../dto
```

---

# 💡 Future Enhancements

* Add file upload (CSV/Excel)
* Automate reconciliation using scheduled jobs
* Add more advanced rule types
* Dashboard & analytics
* Real authentication

---

# 🏁 Conclusion

Recon Lite provides a clean, modular, and scalable structure to handle real-world reconciliation scenarios. Its architecture and workflow align with industry standards and can be extended into a full enterprise-grade reconciliation platform.
