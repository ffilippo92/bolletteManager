# API Specification – Bollette Manager

Documentazione sintetica e completa delle API REST del backend Bollette Manager.  
Tutte le API (eccetto /api/auth/**) richiedono autenticazione JWT tramite header:

```
Authorization: Bearer <token>
```

Risposte in formato **JSON**.

---

# 1. AUTH API

## POST /api/auth/register
Registra un nuovo utente.

### Body esempio
```json
{
  "username": "filippo",
  "email": "filippo@example.com",
  "password": "Password123!"
}
```

### Response
```json
{
  "id": 1,
  "username": "filippo",
  "email": "filippo@example.com"
}
```

---

## POST /api/auth/login
Autentica utente e genera JWT.

### Body
```json
{
  "usernameOrEmail": "filippo",
  "password": "Password123!"
}
```

### Response
```json
{
  "token": "jwt-token",
  "user": {
    "id": 1,
    "username": "filippo",
    "email": "filippo@example.com"
  }
}
```

---

# 2. USER API

## GET /api/users/{id}
Ritorna informazioni dell’utente autenticato.

---

## PUT /api/users/{id}/email
Aggiorna email utente.

### Body
```json
{
  "newEmail": "nuova@example.com"
}
```

---

## POST /api/users/{id}/change-password
Modifica password dell’utente.

### Body
```json
{
  "oldPassword": "Password123!",
  "newPassword": "Password456!"
}
```

---

## DELETE /api/users/{id}
Elimina utente e relative risorse collegate.

---

# 3. BILL API

## POST /api/bills
Crea una nuova bolletta.

### Body esempio
```json
{
  "provider": "ENEL",
  "type": "ELECTRICITY",
  "issueDate": "2025-01-01",
  "dueDate": "2025-01-20",
  "amount": 110.50,
  "status": "PENDING",
  "notes": "Fattura gennaio"
}
```

---

## GET /api/bills
Lista tutte le bollette dell’utente autenticato.

---

## GET /api/bills/{id}
Ottiene i dettagli di una singola bolletta.

---

## PUT /api/bills/{id}
Aggiorna una bolletta esistente.

### Body esempio
```json
{
  "provider": "ENEL",
  "type": "ELECTRICITY",
  "issueDate": "2025-01-01",
  "dueDate": "2025-01-20",
  "amount": 120.00,
  "status": "PAID",
  "notes": "Pagata in data 10/01/2025"
}
```

---

## DELETE /api/bills/{id}
Elimina una bolletta.

---

# 4. BILL DETAIL API

## GET /api/bills/{id}/detail
Recupera il dettaglio della bolletta (se esiste).

---

## POST /api/bills/{id}/detail
Crea o aggiorna il dettaglio bolletta.

### Body esempio
```json
{
  "contractNumber": "ABC123",
  "supplyAddress": "Via Roma 1",
  "billingPeriodStart": "2025-01-01",
  "billingPeriodEnd": "2025-01-31",
  "consumptionKwh": 250,
  "notes": "Consumo stimato"
}
```

---

# 5. BILL ATTACHMENT API

## POST /api/bills/{id}/attachment
Carica un PDF relativo alla bolletta.

### Form-data
```
file: <allegato PDF>
```

---

## GET /api/bills/{id}/attachment
Scarica l’allegato oppure ritorna il metadata associato.

---

# 6. ASSET ACCOUNT API

## POST /api/accounts
Crea un nuovo conto/asset dell’utente.

### Body
```json
{
  "name": "Conto Principale",
  "type": "BANK_ACCOUNT",
  "currency": "EUR",
  "initialBalance": 1000,
  "active": true,
  "description": "Conto corrente principale"
}
```

---

## GET /api/accounts
Ritorna tutti gli account dell’utente.

---

## GET /api/accounts/{id}
Ritorna un singolo account.

---

## PUT /api/accounts/{id}
Aggiorna un account.

---

## DELETE /api/accounts/{id}
Soft delete → imposta `active = false`.

---

# 7. TRANSACTION API

## POST /api/transactions
Crea una transazione.

### Body
```json
{
  "assetAccountId": 1,
  "amount": 50.00,
  "date": "2025-01-15",
  "type": "DEBIT",
  "category": "UTILITIES",
  "description": "Pagamento gas",
  "notes": "Gennaio"
}
```

---

## GET /api/transactions
Lista tutte le transazioni dell’utente.

---

## GET /api/transactions/{id}
Ottiene una singola transazione.

---

## PUT /api/transactions/{id}
Aggiorna una transazione.

---

## DELETE /api/transactions/{id}
Cancella una transazione.

---

## POST /api/transactions/transfer
Trasferisce un importo tra due account.

### Body
```json
{
  "sourceAccountId": 1,
  "destinationAccountId": 2,
  "amount": 300,
  "date": "2025-01-20",
  "description": "Trasferimento risparmi"
}
```

**Effetti**
- crea transazione uscita (DEBIT) per account sorgente  
- crea transazione entrata (CREDIT) per account destinazione  

---

# Sicurezza (JWT)

- Tutte le richieste protette richiedono header:
```
Authorization: Bearer <jwt>
```
- Il token include username/email e claim `userId`
- La multi-utenza è applicata automaticamente nei service via repository `findByUserId()`.

---

# Note finali

Questo documento rappresenta la specifica API essenziale per l’integrazione frontend–backend del progetto Bollette Manager.

