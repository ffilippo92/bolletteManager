# Bollette Manager

Applicazione full-stack per gestire bollette e movimenti: caricamento dati da Excel, associazione dei PDF, salvataggio su PostgreSQL e dashboard con grafici.

## Tecnologie

- **Backend**: Java 21, Spring Boot (Web, Data JPA, Security, Validation)
- **Database**: PostgreSQL
- **Frontend**: Angular + Bootstrap
- **Altro**: Docker, GitHub

## Obiettivi del progetto

- Centralizzare le bollette (luce, gas, acqua, internet, TARI, ecc.)
- Caricare i dati da un file Excel pulito (estratto dalle bollette PDF)
- Associare ogni riga di bolletta al relativo PDF
- Supportare autenticazione utente
- Fornire statistiche e grafici di spesa per periodo/fornitore

## Funzionalità principali

- Autenticazione utente (login)
- Caricamento file Excel con i dati delle bollette
- Upload dei PDF delle bollette e associazione al record
- Lista bollette filtrabile per:
  - anno
  - tipo (luce, gas, ecc.)
  - fornitore
  - stato (pagato / in attesa / cancellato)
- Visualizzazione dettaglio bolletta + collegamento al PDF
- Dashboard con grafici mensili/annuali

## Architettura

- **Backend**: REST API con Spring Boot
  - pattern a layer:
    - Controller
    - Service
    - Repository
    - Entity + DTO
  - Spring Security per protezione delle API
  - Gestione file PDF su file system

- **Frontend**: SPA in Angular
  - Modulo di autenticazione
  - Modulo bollette (lista, dettaglio, upload)
  - Modulo dashboard (statistiche, grafici)

- **Database**:
  - `users`
  - `bills`
  - `bill_files`
  - `movements` 

## Struttura della repository

```text
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
|  ├─ database-schema.md
|  ├─ controller-plan.md
|  └─ roadmap.md
└─ docker-compose.yml
