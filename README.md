# M15SpringRESTAPIMongoDBJWTAuth

## Description
REST API that simulates a dice game where players roll 2 dice in each roll, if the outcome is 7 then it is a Win, otherwise it is a Loss. Also manages the ranking of all the players (including the "anonymous ones) with all CRUD functionalities. Security is applied in a different branch of the project. Data is persisted through MongoDB with referenced documents, the initial data has been loaded from the Main application class, although it could have been loaded from a Runner class.

## Branches
Two different branches in this project:
- **Master** Branch -> No security implemented in this version.
- **Security** Branch -> Security has been implemented with JWT authorization and 3 different roles: "admin", "user", "anonymous".

## Expected Performance
![Authentication](https://user-images.githubusercontent.com/67835708/117442631-52137100-af37-11eb-8a3d-1c8a400e950b.jpg)

![Mongo](https://user-images.githubusercontent.com/67835708/117442832-94d54900-af37-11eb-90e2-e3efcea64d32.png)

![Post](https://user-images.githubusercontent.com/67835708/117443135-fa293a00-af37-11eb-8f4e-6e0bde45b900.png)

## Instructions to run the application:
Open a MongoDB console or editor such as MongoDBCompass. Make use of Postman or ARC to make the calls to the API. In Security branch, in order to be able to perform all the calls to the endpoints, authentication needs to be performerd to get the JWT and paste it in every call.

