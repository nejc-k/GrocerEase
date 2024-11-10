### Podatkovna baza 

- Info -> MongoDB Atlas Database, Cluster0
- User -> user:IigChwsYtIpq8R21
- Database name -> grocerease
- Url -> mongodb+srv://user:IigChwsYtIpq8R21@cluster0.o50mfr6.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0

##### Collections
- User [user_id, name, email, hashed password]
- Items [item_id, trgovina, ime, st_izdelkov, note]
- List [list_id, user_id, items (array of ids)]

##### Api
**Api key** -> FidVM7xwyMRVR9fD9bcyxrhkqkKWY7TBpZWYcEkqc1PQ7rHiLP3sxGkePr6KRPEh

```bash
# Database grocerease, collection users, column name
# Docs -> https://www.mongodb.com/docs/atlas/api/data-api/
curl --location --request POST 'https://eu-central-1.aws.data.mongodb-api.com/app/data-zixhkoc/endpoint/data/v1/action/findOne' \
--header 'Content-Type: application/json' \
--header 'Access-Control-Request-Headers: *' \
--header 'api-key: FidVM7xwyMRVR9fD9bcyxrhkqkKWY7TBpZWYcEkqc1PQ7rHiLP3sxGkePr6KRPEh' \
--data-raw '{
    "collection":"users",
    "database":"grocerease",
    "dataSource":"Cluster0",
    "projection": {"email": 1}
}'
```