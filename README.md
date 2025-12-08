# Bollette Manager v1.0b

Applicazione full-stack per la gestione avanzata di bollette, movimenti finanziari e conti, con autenticazione utente, associazione PDF, dettagli bolletta, multi-utenza e salvataggio dati su PostgreSQL.

## Tecnologie

- **Backend**: Java 21, Spring Boot (Web, Data JPA, Security, Validation)
- **Database**: PostgreSQL
- **Frontend**: Angular
- **Altro**: Docker, GitHub, Postman

---

## Obiettivi del progetto

- Centralizzare la gestione delle bollette (luce, gas, acqua, internet, TARI, ecc.)
- Associare a ogni bolletta il relativo PDF
- Gestire conti bancari, carte e asset finanziari
- Registrare movimenti associati ai vari account
- Fornire autenticazione sicura via JWT
- Implementare multi-utenza: ogni utente vede solo i propri dati
- Fornire statistiche e dashboard analitiche (fase frontend)

---

## Funzionalità principali

### Autenticazione
- Registrazione utente
- Login via JWT
- Rotte protette con Bearer Token
- Recupero e aggiornamento profilo:
  - cambio email
  - cambio password
  - eliminazione utente

---

### Bollette
- Creazione, aggiornamento, eliminazione bolletta
- Filtri base (fornitore, tipo, stato, date)
- Dettaglio bolletta collegato (One-to-One)
- Associazione PDF tramite `BillAttachment`
- Download del PDF associato

---

### Conti e Asset (Asset Accounts)
- Creazione e aggiornamento conti
- Attivazione/disattivazione (soft delete)
- Associazione automatica all’utente loggato
- Elenco conti dell’utente

---

### Movimenti (Transactions)
- Creazione, modifica e cancellazione movimento
- Associazione a un conto finanziario
- Funzione “Transfer Between Accounts”:
  - genera automaticamente un movimento in uscita + uno in entrata
- Filtraggio automatico per utente

---

## Architettura

### Backend: REST API con Spring Boot
Struttura a layer:
- Controller
- Service
- Repository
- Entity + DTO
- Sicurezza tramite Spring Security + JWT  
- Multi-utenza implementata tramite associazione:
      UserEntity 1─N Bills
      UserEntity 1─N Transactions
      UserEntity 1─N AssetAccounts
      e filtri repository `findByUserId(...)`
- Upload PDF tramite endpoint dedicato

### Frontend: Angular (SPA)
(Da Aggiornare)

---

## Database
- `users`  
- `bills`  
- `bill_details`  
- `bill_attachments`  
- `asset_accounts`  
- `transactions`  

---

## Struttura della repository

bollette-manager/
├─ backend/
│  ├─ src/
│  ├─ pom.xml
│  └─ ...
├─ frontend/
│  ├─ src/
│  ├─ package.json
│  └─ ...
├─ docs/
│  ├─ api-spec.md
│  ├─ backend-design.md
│  ├─ frontend-design.md
│  ├─ database-schema.md
└─ docker-compose.yml
