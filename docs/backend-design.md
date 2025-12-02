# Backend Design

## Layer

- Controller
- Service
- Repository
- Entity
- DTO

## Entity principali

### UserEntity
- id
- username
- passwordHash
- roles

### BillEntity
- id
- user
- type
- provider
- periodStart
- periodEnd
- amount
- status
- dueDate
- paymentDate
