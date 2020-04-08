# Run Instructions: 
* Download project

* In case of using Docker please enter `/usg` folder which is project root folder and enter following commands :
1. mvn clean install
2. docker build -t usg-itembase . 
3. docker run -p 3333:8080 usg-itembase 
4. use REST client(Insomnia/Postman) and call -> 

POST `http://localhost:3333/api/currency/convert`

in body You can send JSON for example:
```json
{
 "from": "EUR",
 "to": "USD",
 "amount": 10
}
```  
        
as response You should expect JSON for example:
```json
{
 "from": "EUR",
 "to": "USD",
 "amount": 10.0,
 "converted": 10.78500
}
``` 

* Otherwise please just import current project inside IDE and 
1. start project from main method of`UsgApplication` class  as plain SpringBoot application
2. use REST client(Insomnia/Postman) and call -> 

POST `http://localhost:8080/api/currency/convert`

in body You can send JSON for example:
```json
{
 "from": "EUR",
 "to": "USD",
 "amount": 10
}
``` 
        
as response You should expect JSON for example:
```json
{
 "from": "EUR",
 "to": "USD",
 "amount": 10.0,
 "converted": 10.78500
}
``` 
