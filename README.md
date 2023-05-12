# Roles Service

For the challenge, I built the project with the idea of creating the Roles service, aiming to structure the project in a way that each class is responsible for a single task and they perform the necessary actions.

I leveraged the advantages provided by Spring Boot as a framework, using interfaces and implementations to properly separate the different layers of logic.

Thus, I created different interfaces (RoleService, MemberService) to be able to implement them and make the program easily extendable.

Firstly, I created the service layer `/roles` which is used for creating roles, assigning them, and performing queries. Depending on each service, I utilized the endpoints provided by the challenge initially to retrieve information about teams and users.

When assigning a role, I performed validations to check if the user exists and if they belong to the provided team before assigning the role.

Recognizing the concept of Membership, I generated the necessary beans, interfaces, and services to handle this new entity, effectively separating responsibilities.

I also implemented annotations to assist with logging necessary information and created exceptions to display error messages when errors occurred.

## Prerequisites

- Java 11 or later
- Maven 3.6 or later
- Docker
- PostgreSQL (on docker-compose)

## Building the Project

1. Clone the repository:
 ```bash
git clone https://github.com/padagoal/roleservice
```

2. Build the project using Maven:

 ```bash
cd ./RoleService

mvn compile package -DskipTests
```

3. Build the image
 ```bash
docker build -t rol-service-docker:rol-service .
```

## Running the APP (docker-compose)

1. Start the service using docker compose
 ```bash
cd ./RoleService

docker-compose up
 ```

## API Documentation

Postman collection available here:
```css
https://api.postman.com/collections/7168827-e3e0bb77-fb8a-4a5b-a801-925691b08849?access_key=PMAT-01H08EPGCNJSEHVD70QXJ6JHXW
```

or just import the collection file `E-core challenge.postman_collection.json` into Postman

## GET /roles/
This endpoint list all the Roles 

#### Request
```css
GET /roles/
```
#### Response
```css
200 OK 
Content-Type: application/json
[{"id":"9df1a424-afe1-45bc-adf8-5c4aaf4fedf9","name":"Developer","isDefault":true},{"id":"5c1e624-bda1-45bc-adf9-5c4ff4fedf9","name":"Product Owner","isDefault":false},{"id":"8ae1e424-bda1-45bc-bde8-5c4aed4fedf9","name":"Tester","isDefault":false},{"id":"2856fa7d-f439-4fa9-833b-a725b33be07e","name":"Scrum Master","isDefault":false}]
```

## POST /roles/
This endpoint creates a Role

#### Request
```css
POST /roles/
{
    "name":"Scrum Master"
}
```
#### Response
```css
200 OK
Content-Type: application/json
{"id":"2856fa7d-f439-4fa9-833b-a725b33be07e","name":"Scrum Master"}
```

## POST /roles/assign
This endpoint assign a role to a team member, usign his userID and TeamID and roleId

#### Request
```css
POST /roles/assign

{
    "userId":"371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
    "teamId":"7676a4bf-adfe-415c-941b-1739af07039b",
    "rolId":"1908a3b0-55c8-449e-83cd-4ea693405f2c"
}

```

#### Response
```css
200 OK
Content-Type: application/json
{
"id": "5cfe0795-05c5-4a94-bded-d104cb9bb0f9",
"userid": "197c2b23-1218-44d0-b6b8-d757ba004515",
"teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
"rolId": {
       "id": "2856fa7d-f439-4fa9-833b-a725b33be07e",
       "name": "Scrum Master"
       }
}
```

### Considerations
- If the request doesn't not have a roleID, the default role will be assigned to the userID
- If the request, assigns a new role to already created membership with a role, it's going to be updated with the new role in request

#### Request
```css
POST /roles/assign

{
    "userId":"371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
    "teamId":"7676a4bf-adfe-415c-941b-1739af07039b"
}
```
#### Response
```css
200 OK
Content-Type: application/json
{
    "id": "5cfe0795-05c5-4a94-bded-d104cb9bb0f9",
    "userid": "197c2b23-1218-44d0-b6b8-d757ba004515",
    "teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
    "rolId": {
           "id": "9df1a424-afe1-45bc-adf8-5c4aaf4fedf9",
           "name": "Developer"
        }
}
```
## Errors

If a userID, roleId or teamId is not found, the service throws a Exception with the proper message

#### Response
```css
404 NOT FOUND
Content-Type: application/json
{
    "timestamp": "12-05-2023 04:27:58",
    "status": "NOT_FOUND",
    "message": "Resource Not Found",
    "errors": [
        "The given roleId [1908a3b0-55c8-449e-83cd-4ea693405f2c11] was not found"
    ]
}


404 NOT FOUND
Content-Type: application/json
{
    "timestamp": "12-05-2023 04:40:04",
    "status": "NOT_FOUND",
    "message": "Resource Not Found",
    "errors": [
        "The team ID: [b047d3f4-3469-47ce-a03f-1637a6de036b] was not found"
    ]
}
```

## GET /roles/{roleId}/memberships
This endpoint look up all the memberships assigned to a role

#### Request
```css
GET /roles/8d106842-af08-4e5d-883f-ea4646b0c8e9/memberships

```

#### Response
```css
200 OK
Content-Type: application/json
[
    {
        "id": "40a88ba0-8d85-40a8-b836-a0aa1763730b",
        "userId": "54383a18-425c-4f50-9424-1c4c27e776dd",
        "teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
        "role": {
              "id": "8d106842-af08-4e5d-883f-ea4646b0c8e9",
              "name": "DBA",
              "isDefault": false
            }
    }
]
```
#### Request
```css
GET /roles/9df1a424-afe1-45bc-adf8-5c4aaf4fedf9/memberships
```
#### Response
```css


200 OK
Content-Type: application/json
[
    {
        "id": "4500dff4-677c-4980-a958-e600de9d0e30",
        "userId": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
        "teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
        "role": {
              "id": "9df1a424-afe1-45bc-adf8-5c4aaf4fedf9",
              "name": "Developer",
              "isDefault": true
              }
    },
    {
        "id": "b58b1416-35cf-4cdd-b430-a5ced4629334",
        "userId": "e947058e-2d5f-47d9-925b-27bcab14c38e",
        "teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
        "role": {
              "id": "9df1a424-afe1-45bc-adf8-5c4aaf4fedf9",
              "name": "Developer",
              "isDefault": true
              }
        },
    {
        "id": "5cfe0795-05c5-4a94-bded-d104cb9bb0f9",
        "userId": "197c2b23-1218-44d0-b6b8-d757ba004515",
        "teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
        "role": {
              "id": "9df1a424-afe1-45bc-adf8-5c4aaf4fedf9",
              "name": "Developer",
              "isDefault": true
              }
    }
]
```
## GET /roles/teams/{teamid}/users/{userid}
This endpoint look up the role assigned to specif membership

#### Request
```css
/roles/teams/7676a4bf-adfe-415c-941b-1739af07039b/users/371d2ee8-cdf4-48cf-9ddb-04798b79ad9e
```

#### Response
```css
{
    "id": "4500dff4-677c-4980-a958-e600de9d0e30",
    "userId": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
    "teamId": "7676a4bf-adfe-415c-941b-1739af07039b",
    "role": {
        "id": "9df1a424-afe1-45bc-adf8-5c4aaf4fedf9",
        "name": "Developer",
        "isDefault": true
    }

}
```


## Suggestion for improvement in the Team or User services

- One of suggestions maybe it's to improve the speed of the response, I will think maybe it's a servless service and has his cold start.
- When I make a POST request to /users/, it doesn't respond with an error or anything; it responds as if a GET request had been made
- For the users, add a list of the teams they're member of.
- For teams and users, add Status, if they Active, Inactive, Blocked.
