# BolletteManager – Backend

## Fase 2 e Fase 3 di sviluppo

---

## Introduzione e modalità di lavoro

A partire da questa fase, lo sviluppo del backend segue una struttura a **branch ben definita**, con l’obiettivo di rendere il progetto più ordinato, scalabile e coerente con le pratiche utilizzate in ambito professionale.

Il repository non è più gestito lavorando direttamente su `main`, ma viene suddiviso in:

* **`main`**: contiene solo versioni stabili e presentabili del progetto
* **`develop`**: branch di integrazione continua, dove confluiscono le funzionalità completate
* **`feature/*`**: branch dedicati allo sviluppo di singole funzionalità o miglioramenti

Ogni nuova attività viene sviluppata partendo da `develop`, creando una branch `feature/*`. Una volta completata e verificata la funzionalità (build, test, controlli di qualità), il codice viene reintegrato in `develop`. Al termine di una fase significativa di lavoro, `develop` viene infine allineato a `main`.

---

## FASE 2 – Qualità del codice, test e automazione

La Fase 2 è focalizzata sul consolidamento del backend esistente, portandolo a uno standard qualitativo adeguato a un progetto professionale.

### Code Quality

* Configurazione di **Spotless** e **Checkstyle**
* Integrazione di **SonarCloud**
* Analisi e risoluzione dei principali code smell iniziali
* Documentazione delle regole di stile e convenzioni adottate

### Testing

* Scrittura di **8–12 test unitari** sui componenti principali
* Aggiunta di **3–5 test di integrazione** tramite Testcontainers
* Definizione di una soglia di coverage accettabile
* Documentazione della strategia di test

### Docker & Docker Compose

* Creazione del **Dockerfile** per il backend
* Creazione di un **docker-compose.yml** con applicazione e database
* Validazione dell’avvio completo dell’ambiente containerizzato
* Aggiornamento del README con le istruzioni per l’ambiente locale

### CI/CD

* Creazione di una pipeline **CI** (build, test, analisi statica)
* Creazione di una pipeline **CD** (build immagine Docker e push)
* Inserimento dei badge di stato nel README
* Validazione delle pipeline tramite commit reali

> La Fase 2 si considera conclusa quando il progetto è completamente automatizzato, testato e verificabile tramite pipeline.

---

## FASE 3 – Osservabilità, monitoraggio e deploy

La Fase 3 introduce aspetti avanzati di osservabilità e deploy, con l’obiettivo di rendere il backend realmente pronto per un contesto produttivo.

### Logging & ELK

* Configurazione di **Logback** con logging in formato JSON
* Aggiunta di campi MDC (traceId, userId)
* Setup di **Elasticsearch**, **Kibana** e **Filebeat**
* Creazione di query Kibana e raccolta di screenshot documentativi

### Monitoring

* Integrazione di **Spring Actuator** e **Micrometer** con Prometheus
* Aggiunta di Prometheus al docker-compose
* Configurazione di dashboard Grafana (latenza, RPS, errori)
* Inserimento di screenshot nella documentazione

### AWS Deployment

* Creazione e configurazione di una istanza **EC2**
* Installazione di Docker sull’istanza
* Deploy del backend tramite Docker Compose
* Configurazione di **SSM Parameter Store**
* Verifica delle metriche su **CloudWatch**
* Creazione di un diagramma architetturale AWS

### Documentazione

* `ARCHITECTURE.md`
* `OBSERVABILITY.md`
* `DEVOPS-PIPELINE.md`

---

