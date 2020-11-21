# Spring-data-jpa-msaccess
Sample API project with MS Access DB in Spring Boot

## Components used:
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- Project Lombok
- UCanAccess (An open-source Java JDBC driver implementation to read/write Microsoft Access databases).

## Set-up
- Clone the Repository.
- In application.properties file set db.path property with path of **.accdb** file 
- Run command "bootRun" on console or use gradle task -> application -> bootRun
- On the browser run : "http://localhost:8002/"

When prompted with login use the following credentials.
###### User1:
- User name: testadmin
- Password: adminpassword
###### User2:
- User name: testUser
- Password: userpassword

After Login run the following on browser along with below mentioned Query params.
http://localhost:8002/accountStatement/fetch

### Query Params:
- accountId (**Mandatory**) (type: Long)
- fromDate (Optional) (type: LocalDate, pattern = "dd/MM/yyyy")
- toDate (Optional) (type: LocalDate, pattern = "dd/MM/yyyy")
- fromValue (Optional) (type: Double)
- toValue (Optional) (type: Double)
