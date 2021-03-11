# Fetch Rewards Application


### 1. Add Transaction

endpoint - POST /fetch-rewards/add-transaction

body - 
{
"payer" : payer,
"points" : 100,
"timestamp" : "yyyy-MM-dd'T'HH:mm:ss'Z'"
}

response - 201 CREATED



### 2. Add Transaction Bulk

endpoint - POST /fetch-rewards/add-transaction-bulk

body - 
[{
"payer" : payer,
"points" : points,
"timestamp" : "yyyy-MM-dd'T'HH:mm:ss'Z'"
}]

response - 201 CREATED



### 3. Spend Points

endpoint - POST /fetch-rewards/spend-points

body - 
{
"points" : 5000
}

response code - 200 OK or 500 Internal Server Error (In case of insufficient points)

response body - 
[{
"payer" : payer,
"points" : points
}]



### 4. Get All Payer Info

endpoint - GET /fetch-rewards/get-balances

response code - 200 OK

response body - 
[{
"payer" : payer,
"points" : points
}]