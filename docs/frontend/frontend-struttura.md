## Struttura Frontend

src/
 └─ app/
     ├─ core/
     │   ├─ auth/
     │   │   ├─ auth.service.ts
     │   │   ├─ auth.guard.ts
     │   │   ├─ auth.interceptor.ts
     │   │   ├─ jwt-token-storage.service.ts
     │   │   └─ auth.model.ts
     │   ├─ services/
     │   │   ├─ user.service.ts
     │   │   ├─ notification.service.ts
     │   │   └─ error-handler.service.ts
     │   ├─ interceptors/
     │   │   └─ http-error.interceptor.ts
     │   ├─ guards/
     │   │   └─ role.guard.ts (se servirà)
     │   ├─ layout/
     │   │   ├─ main-layout/
     │   │   │   ├─ main-layout.component.ts/html/scss
     │   │   └─ auth-layout/
     │   │       ├─ auth-layout.component.ts/html/scss
     │   ├─ core.module.ts
     │   └─ index.ts
     │
     ├─ shared/
     │   ├─ components/
     │   │   ├─ page-header/
     │   │   ├─ confirm-dialog/
     │   │   ├─ table/
     │   │   └─ form-controls/ (input, select, date-picker wrappers)
     │   ├─ pipes/
     │   │   ├─ currency-format.pipe.ts
     │   │   ├─ date-format.pipe.ts
     │   │   └─ bill-type-label.pipe.ts
     │   ├─ directives/
     │   ├─ models/      (DTO lato frontend)
     │   ├─ shared.module.ts
     │   └─ index.ts
     │
     ├─ features/
     │   ├─ auth/
     │   │   ├─ login/
     │   │   ├─ register/ (se prevista)
     │   │   ├─ auth-routing.module.ts
     │   │   └─ auth.module.ts
     │   ├─ dashboard/
     │   │   ├─ components/
     │   │   ├─ dashboard-routing.module.ts
     │   │   └─ dashboard.module.ts
     │   ├─ bills/
     │   │   ├─ pages/
     │   │   │   ├─ bill-list/
     │   │   │   ├─ bill-detail/
     │   │   │   └─ bill-edit/
     │   │   ├─ components/
     │   │   │   ├─ bill-filters/
     │   │   │   └─ bill-summary-card/
     │   │   ├─ services/
     │   │   │   ├─ bill.service.ts
     │   │   │   ├─ bill-attachment.service.ts
     │   │   │   └─ bill-detail.service.ts
     │   │   ├─ bills-routing.module.ts
     │   │   └─ bills.module.ts
     │   ├─ transactions/
     │   │   ├─ pages/
     │   │   ├─ services/transaction.service.ts
     │   │   ├─ transactions-routing.module.ts
     │   │   └─ transactions.module.ts
     │   ├─ asset-accounts/
     │   │   ├─ pages/
     │   │   ├─ services/asset-account.service.ts
     │   │   ├─ asset-accounts-routing.module.ts
     │   │   └─ asset-accounts.module.ts
     │   └─ profile/
     │       ├─ pages/
     │       ├─ profile-routing.module.ts
     │       └─ profile.module.ts
     │
     ├─ app-routing.module.ts
     ├─ app.component.ts/html/scss
     └─ app.module.ts
