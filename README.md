# Banking Demo Application

## Build

- check out project `git clone https://github.com/andreikubar/banking-demo.git`
- run build `mvn clean package`

## Start application

- make sure local port 8080 is free
- start the app `java -jar target/banking-demo-0.0.1-SNAPSHOT.jar`

## Usage

You can accomplish the following actions using this demo: 
- Request the account statement along with transactions using a `GET` call to `/accounts/statement/{id}` 
  where `{id}` is the account number. There are 2 preloaded accounts to be used with IDs "1" and "2".
- Send money between accounts using a `POST` call to `/accounts/transfer`. The request must contain
  a payload of content type `application/json`, having the following format: `{"sourceAccount":"{string}", "targetAccount":"{string}", "amount":{number}}`.
  Accounts with the given account numbers must exist and the amount must be a positive integer and cannot be larger than the balance on the source account.   

## REST api

Title            | Get account statement with the list of transactions
-----------------|----------------
URL              | /accounts/statement/{id}
Method           | GET
URL Params       | **Required**: {id} - account number 
Success response | Example: `{"accountInfo":{"accountNumber":"1", "balance":980}, "transactions":[{"transactionId":1, "amount":-10}, {"transactionId":3, "amount":-10}]}`
Example call     | `curl -X GET "http://localhost:8080/accounts/statement/1"`

Title            | Send money between two accounts
-----------------|----------------
URL              | /accounts/transfer
Method           | POST
URL Params       | None
Data Params      | Example: `{"sourceAccount":"1", "targetAccount":"2", "amount":10}` 
Success response | Status code 200
Failure response | Status code 400: Account does not exist or amount is larger than the balance or amount is a non-positive number
Example call     | `curl -X POST "http://localhost:8080/accounts/transfer" -d '{"sourceAccount":"1", "targetAccount":"2", "amount":10}' --header "Content-Type:application/json"` 