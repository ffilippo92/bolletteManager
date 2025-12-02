# Controller Plan – Bollette Manager

## AuthController

- POST /api/auth/login  
  - Input: LoginRequestDTO  
  - Output: AuthResponseDTO (token + user info)  

- POST /api/auth/register (opzionale)

---

## BillController

- GET /api/bills  
  - parametri: year, type, provider  
  - output: List<BillResponseDTO>

- GET /api/bills/{id}  
  - output: BillDetailsDTO

- POST /api/bills/upload-excel  
  - input: file Excel  
  - output: lista bollette create

- POST /api/bills/{id}/upload-pdf  
  - input: PDF  
  - output: BillFileResponseDTO

- DELETE /api/bills/{id}  
  - output: success/fail

---

## FileController (opzionale)

- GET /api/files/{id}/download  
  - output: PDF della bolletta
