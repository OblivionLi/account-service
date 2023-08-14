# account service
#### This application serves as the backend for a website, facilitating the distribution of company payrolls to employees. It comprises various endpoints that enable users to log in, retrieve payments, submit new payments, update passwords, access event logs for each endpoint, and provides administrative endpoints for comprehensive management. These endpoints are secured by roles and permissions. Moreover, the application offers the capability to retrieve employee payrolls either based on specific time periods or as a complete list.
---
##### Examples
---
```
Example 1: a GET request for api/auth/signup under the Auditor role

Response: 200 OK

Request body:

[
{
  "id" : 1,
  "date" : "<date>",
  "action" : "CREATE_USER",
  "subject" : "Anonymous", \\ A User is not defined, fill with Anonymous
  "object" : "johndoe@acme.com",
  "path" : "/api/auth/signup"
}, {
  "id" : 6,
  "date" : "<date>",
  "action" : "LOGIN_FAILED",
  "subject" : "maxmustermann@acme.com",
  "object" : "/api/empl/payment", \\ the endpoint where the event occurred
  "path" : "/api/empl/payment"
}, {
  "id" : 9,
  "date" : "<date>",
  "action" : "GRANT_ROLE",
  "subject" : "johndoe@acme.com",
  "object" : "Grant role ACCOUNTANT to petrpetrov@acme.com",
  "path" : "/api/admin/user/role"
}, {
  "id" : 10,
  "date" : "<date>",
  "action" : "REMOVE_ROLE",
  "subject" : "johndoe@acme.com",
  "object" : "Remove role ACCOUNTANT from petrpetrov@acme.com",
  "path" : "/api/admin/user/role"
}, {
  "id" : 11,
  "date" : "<date>",
  "action" : "DELETE_USER",
  "subject" : "johndoe@acme.com",
  "object" : "petrpetrov@acme.com",
  "path" : "/api/admin/user"
}, {
  "id" : 12,
  "date" : "<date>",
  "action" : "CHANGE_PASSWORD",
  "subject" : "johndoe@acme.com",
  "object" : "johndoe@acme.com",
  "path" : "/api/auth/changepass"
}, {
  "id" : 16,
  "date" : "<date>",
  "action" : "ACCESS_DENIED",
  "subject" : "johndoe@acme.com",
  "object" : "/api/acct/payments", \\ the endpoint where the event occurred
  "path" : "/api/acct/payments"
}, {
  "id" : 25,
  "date" : "<date>",
  "action" : "BRUTE_FORCE",
  "subject" : "maxmustermann@acme.com",
  "object" : "/api/empl/payment", \\ the endpoint where the event occurred
  "path" : "/api/empl/payment"
}, {
  "id" : 26,
  "date" : "<date>",
  "action" : "LOCK_USER",
  "subject" : "maxmustermann@acme.com",
  "object" : "Lock user maxmustermann@acme.com",
  "path" : "/api/empl/payment" \\ the endpoint where the lock occurred
}, {
  "id" : 27,
  "date" : "<date>",
  "action" : "UNLOCK_USER",
  "subject" : "johndoe@acme.com",
  "object" : "Unlock user maxmustermann@acme.com",
  "path" : "/api/admin/user/access"
}
]
```
---
```
Example 2: a POST request for /api/admin/user/role

Request body:

{
   "user": "administrator@acme.com",
   "role": "AUDITOR",
   "operation": "GRANT" 
}
Response: 400 Bad Request

Response body:

{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "The user cannot combine administrative and business roles!",
    "path": "/api/admin/user/role"
}
```
---
```
Example 3: a PUT request for PUT api/admin/user/access

Request body:

{
   "user": "administrator@acme.com",
   "operation": "LOCK" 
}
Response: 400 Bad Request

Response body:

{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "Can't lock the ADMINISTRATOR!",
    "path": "/api/admin/user/access"
}
```
---
```
Example 4: a PUT request for PUT api/admin/user/access

Request body:

{
   "user": "user@acme.com",
   "operation": "LOCK" 
}
Response: 200 OK

Response body:

{
    "status": "User user@acme.com locked!"
}
```
---
```
Example 5: a PUT request for /api/admin/user/role with the correct authentication under the Administrator role

Request:

{
   "user": "ivanivanov@acme.com",
   "role": "ACCOUNTANT",
   "operation": "GRANT"
}
Response: 200 OK

Response body:

{
    "id": 2,
    "name": "Ivan",
    "lastname": "Ivanov",
    "email": "ivanivanov@acme.com",
    "roles": [
        "ROLE_ACCOUNTANT",
        "ROLE_USER"
    ]
}
```
---
```
Example 6: a GET request for /api/empl/payment with the correct authentication under the Administrator role

Response: 200 OK

Response body:

[
    {
        "id": 1,
        "name": "John",
        "lastname": "Doe",
        "email": "johndoe@acme.com",
        "roles": [
            "ROLE_ADMINISTRATOR"
        ]
    },
    {
        "id": 2,
        "name": "Ivan",
        "lastname": "Ivanov",
        "email": "ivanivanov@acme.com",
        "roles": [
            "ROLE_ACCOUNTANT",
            "ROLE_USER"
        ]
    }
]
```
---
```
Example 7: a PUT request for /api/admin/user/role with the correct authentication under the Administrator role

Request:

{
   "user": "ivanivanov@acme.com",
   "role": "ACCOUNTANT",
   "operation": "REMOVE"
}
Response: 200 OK

Response body:

{
    "id": 2,
    "name": "Ivan",
    "lastname": "Ivanov",
    "email": "ivanivanov@acme.com",
    "roles": [
        "ROLE_USER"
    ]
}
```
---
```
Example 8: a GET request for /api/admin/user/role with the correct authentication under the User role

Response: 403 Forbidden

Response body:

{
    "timestamp": "<date>",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied!",
    "path": "/api/admin/user/"
}
```
---
```
Example 9: a DELETE request for /api/admin/user/ivanivanov@acme.com with the correct authentication under the Administrator role

Response: 200 OK

Response body:

{
    "user": "ivanivanov@acme.com",
    "status": "Deleted successfully!"
}
```
---
```
Example 10: a DELETE request for /api/admin/user/johndoe@acme.com with the correct authentication under the Administrator role

Response: 400 Bad Request

Response body:

{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "Can't remove ADMINISTRATOR role!",
    "path": "/api/admin/user/johndoe@acme.com"
}
```
---
```
Example 11: a DELETE request for /api/admin/user/ivanivanov@acme.com with the correct authentication under the Administrator role

Response: 404 Not Found

Response body:

{
    "timestamp": "<date>",
    "status": 404,
    "error": "Not Found",
    "message": "User not found!",
    "path": "/api/admin/user/ivanivanov@acme.com"
}
```


