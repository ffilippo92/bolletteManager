# Database Schema – Bollette Manager

## Panoramica
Il database utilizza PostgreSQL e segue un modello relazionale con supporto alla multi-utenza.  
Le entità principali sono: **User**, **Bill**, **BillDetail**, **BillAttachment**, **AssetAccount**, **Transaction**.

Segue la documentazione tecnica delle tabelle, chiavi e relazioni.

---

# 1. USERS

| Colonna      | Tipo        | Note                                  |
|--------------|-------------|----------------------------------------|
| id           | BIGSERIAL   | PK                                     |
| username     | VARCHAR     | Unico                                  |
| email        | VARCHAR     | Unico                                  |
| password     | VARCHAR     | Hash BCrypt                            |
| roles        | VARCHAR     | Ruolo applicativo (es. ROLE_USER)      |

---

# 2. BILLS

| Colonna       | Tipo        | Note                              |
|---------------|-------------|-----------------------------------|
| id            | BIGSERIAL   | PK                                |
| provider      | VARCHAR     | Fornitore (ENEL, IREN, ecc.)      |
| type          | VARCHAR     | Tipo bolletta (gas, luce…)        |
| issue_date    | DATE        | Data emissione                    |
| due_date      | DATE        | Scadenza                          |
| amount        | NUMERIC     | Importo                           |
| status        | VARCHAR     | PENDING, PAID, CANCELED           |
| notes         | TEXT        | Note interne                      |
| user_id       | BIGINT      | FK → users(id)                    |

**Relazioni**
- 1 Bill = 1 BillDetail (opzionale)
- 1 Bill = 1 BillAttachment (opzionale)
- 1 User = N Bills

---

# 3. BILL_DETAILS

| Colonna             | Tipo        | Note                         |
|---------------------|-------------|------------------------------|
| id                  | BIGSERIAL   | PK                           |
| contract_number     | VARCHAR     | Numero contratto             |
| supply_address      | VARCHAR     | Indirizzo fornitura          |
| billing_period_start| DATE        | Inizio periodo               |
| billing_period_end  | DATE        | Fine periodo                 |
| consumption_kwh     | NUMERIC     | Dati consumo                 |
| notes               | TEXT        |                              |
| bill_id             | BIGINT      | FK → bills(id), UNIQUE       |

---

# 4. BILL_ATTACHMENTS

| Colonna       | Tipo        | Note                                  |
|---------------|-------------|----------------------------------------|
| id            | BIGSERIAL   | PK                                     |
| filename      | VARCHAR     | Nome file originale                    |
| filepath      | VARCHAR     | Percorso sul filesystem                |
| upload_date   | TIMESTAMP   | Data caricamento                       |
| bill_id       | BIGINT      | FK → bills(id), UNIQUE                 |

---

# 5. ASSET_ACCOUNTS

| Colonna       | Tipo        | Note                               |
|---------------|-------------|-------------------------------------|
| id            | BIGSERIAL   | PK                                  |
| name          | VARCHAR     | Nome account                        |
| type          | VARCHAR     | BANK_ACCOUNT, CARD, CASH…           |
| currency      | VARCHAR     | EUR, USD…                           |
| initial_balance | NUMERIC   | Saldo iniziale                      |
| active        | BOOLEAN     | Soft delete                         |
| description   | TEXT        |                                     |
| user_id       | BIGINT      | FK → users(id)                      |

**Relazioni**
- 1 User = N AssetAccounts
- 1 Account = N Transactions

---

# 6. TRANSACTIONS

| Colonna       | Tipo        | Note                                  |
|---------------|-------------|----------------------------------------|
| id            | BIGSERIAL   | PK                                     |
| amount        | NUMERIC     | Importo (+ entrata, - uscita)          |
| date          | DATE        | Data transazione                       |
| type          | VARCHAR     | DEBIT / CREDIT                         |
| category      | VARCHAR     | UTILITIES, RENT, FOOD, ecc.            |
| description   | TEXT        |                                        |
| notes         | TEXT        |                                        |
| asset_account_id | BIGINT   | FK → asset_accounts(id)                |
| user_id       | BIGINT      | FK → users(id)                         |

---

# 7. RELAZIONI PRINCIPALI (Riassunto)

User 1 ──── N Bills
User 1 ──── N AssetAccounts
User 1 ──── N Transactions
Bill 1 ──── 1 BillDetail
Bill 1 ──── 1 BillAttachment
AssetAccount 1 ──── N Transactions

---

# 8. Note tecniche

- In fase di sviluppo lo schema viene generato da Hibernate (`ddl-auto=update`).
- In produzione si consiglia `ddl-auto=validate` + migrazioni Flyway.
- Ogni tabella legata all’utente implementa la multi-utenza tramite `user_id`.

