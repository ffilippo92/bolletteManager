# BolletteManager – Backend Summary FASE 1

Nota:
Le funzionalità esposte qui potrebbero variare rispetto allo svluppo futuro del progetto. Qui sotto verranno riportate le funzionalità base del progetto in fase iniziale di creazione e progettazione.
Per ulteriori informazioni verificare anche il Markdown backend-sommario per le fasi 2 e 3.

## Tecnologie principali
- **Spring Boot 4**
- **Spring Web / MVC**
- **Spring Data JPA / Hibernate**
- **Spring Security + JWT**
- **Lombok**
- **PostgreSQL**
- **Maven**

---

# 1. Architettura generale

Il backend segue una struttura a livelli chiara:

controller → service → repository → entity → database


Ogni entità ha:
- DTO dedicati
- Service + ServiceImpl
- Controller REST
- Repository JPA

La sicurezza è centralizzata in:
- `SecurityConfig`
- `JwtService`
- `JwtAuthenticationFilter`
- `UserDetailsServiceImpl`
- `AuthController`

---

# 2. Sicurezza e autenticazione

### Autenticazione
- **JWT stateless**  
- Endpoint pubblici: `/api/auth/**`
- Tutte le altre API richiedono `Bearer <token>`

### Componenti principali
- `AuthController`
- `JwtService`
- `JwtAuthenticationFilter`
- `SecurityConfig`
- `UserDetailsServiceImpl`

### Token
- Contiene `sub` (username/email) e claim `userId`
- Validato ad ogni richiesta dal filtro JWT
- L’utente corrente è ottenuto tramite `SecurityContextHolder`

---

# 3. Multi–utenza

Ogni risorsa appartiene sempre e solo all’utente autenticato.

### Entity collegate all’utente:
- BillEntity (`@ManyToOne User`)
- AssetAccountEntity (`@ManyToOne User`)
- TransactionEntity (`@ManyToOne User`)

### Enforcement dell’ownership:
- Nei repository tramite metodi:  
  - `findByUserId(...)`  
  - `findByIdAndUserId(...)`
- Nei service tramite `CurrentUserService.getCurrentUserId()`

---

# 4. Entity principali e relazioni

### User
- id, username, email, password, roles
- Relazioni:
  - 1–N Bills
  - 1–N AssetAccounts
  - 1–N Transactions

### Bill
- provider, type, dates, amount, status, notes, etc.
- Relazioni:
  - 1–1 BillDetail
  - 1–1 BillAttachment
  - N–1 User

### BillDetail
- contractNumber, address, periodStart, periodEnd, consumption

### BillAttachment
- info PDF o file associato alla bolletta
- storage implementabile via filesystem o DB

### AssetAccount
- type, currency, name, balance, active

### Transaction
- amount, type (DEBIT/CREDIT), category, date, notes

---

# 5. Controller e endpoint

## 5.1 AuthController
POST /api/auth/register
POST /api/auth/login

## 5.2 UserController
GET /api/users/{id}
DELETE /api/users/{id}
POST /api/users/{id}/change-password
PUT /api/users/{id}/email

## 5.3 AssetAccountController
POST /api/accounts
GET /api/accounts
GET /api/accounts/{id}
PUT /api/accounts/{id}
DELETE /api/accounts/{id}

## 5.4 TransactionController
POST /api/transactions
GET /api/transactions
GET /api/transactions/{id}
PUT /api/transactions/{id}
DELETE /api/transactions/{id}
POST /api/transactions/transfer

## 5.5 BillController
POST /api/bills
GET /api/bills
GET /api/bills/{id}
PUT /api/bills/{id}
DELETE /api/bills/{id}

## 5.6 BillDetailController
GET /api/bills/{id}/detail
POST /api/bills/{id}/detail

## 5.7 BillAttachmentController
POST /api/bills/{id}/attachment (multipart/form-data)
GET /api/bills/{id}/attachment

---

# 6. DTO principali

### Auth
- `LoginRequestDto`
- `RegisterRequestDto`
- `AuthResponseDto`

### User
- `UserDto`
- `ChangePasswordDto`
- `ChangeEmailDto`

### AssetAccount
- `AssetAccountDto`

### Transaction
- `TransactionDto`
- `TransactionSearchCriteriaDto` (se implementato)

### Bill
- `BillRequestDto`
- `BillResponseDto`
- `BillDetailDto`
- `BillAttachmentDto`

---

# 7. Service

Ogni *ServiceImpl*:
- applica business logic
- applica filtri di sicurezza:
  - verifica ownership (`findByIdAndUserId`)
- gestisce mappature Entity ↔ DTO

Service principali:
- `AuthService`
- `UserService`
- `AssetAccountService`
- `TransactionService`
- `BillService`
- `BillDetailService`
- `BillAttachmentService`

---

# 8. Database

- PostgreSQL  
- Schema generato in sviluppo via `ddl-auto=update`  
- Multiutenza tramite colonne `user_id`

Tabelle principali:
- `users`
- `bills`
- `bill_details`
- `bill_attachments`
- `asset_accounts`
- `transactions`

---

# 9. Postman

È disponibile una collection completa:
- tutte le API organizzate in cartelle
- variabili globali (`baseUrl`, `jwt`, etc.)
- autenticazione automatica con Bearer Token

---

# 10. Stato del backend

La **Fase 1 del backend è completa e stabile**:


