Cinema implemented in Java.
- 

Built With:
- 
- Spring MVC, Data, Security, Webflow
- Web-server: Tomcat
- Hibernate
- MySQL
- Packaging: Apache Maven
- The inner structure was created according to N-Tier architecture and SOLID principles

A customer can:
-
- view a list of all films
- view a list of all cinema halls
- check all available films to a particular date
- add a ticket to the shopping cart
- delete a ticket from the shopping cart
- complete the order
- view customer's order history


An admin can:
- 
- view a list of all films
- find user by email
- add new film
- create new cinema hall
- create new movie session

Configuration:
-
- Tomcat (Local):
Deployment - war_exploded, context address - "/"
- Admin and User will be added to your data base during program start. Like the rest data.
        `Username: user@gmail.com; password: 1111`
        `Username: admin@gmail.com; password: 0000`



