from pymongo import MongoClient
import bcrypt

uri = "mongodb+srv://user:IigChwsYtIpq8R21@cluster0.o50mfr6.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
client = MongoClient(uri)

# Database name
db = client['grocerease']

### Primeri vstavljanja v bazo
## Items
items_collection = db['items']
items = [
    {"item_id": 1, "name": "Milk"},
    {"item_id": 2, "name": "Bread"},
    {"item_id": 3, "name": "Eggs"},
    {"item_id": 4, "name": "Butter"},
    {"item_id": 5, "name": "Cheese"}
]
items_collection.insert_many(items)

## Users
user_collection = db['users']
def hash_password(password):
    salt = bcrypt.gensalt() 
    hashed = bcrypt.hashpw(password.encode('utf-8'), salt)  
    return hashed

users = [
    {"user_id": 1, "name": "Alice", "email": "alice@example.com", "password": hash_password("securePassword1").decode("utf-8")},
    {"user_id": 2, "name": "Bob", "email": "bob@example.com", "password": hash_password("securePassword2").decode("utf-8")},
    {"user_id": 3, "name": "Charlie", "email": "charlie@example.com", "password": hash_password("securePassword3").decode("utf-8")}
]

user_collection.insert_many(users)
print("Users with hashed passwords added.")

## Lists
list_collection = db['lists']
lists = [
    {"list_id": 1, "items": [1, 2, 3], "user_id": 1},  # List for Alice with Milk, Bread, and Eggs
    {"list_id": 2, "items": [4, 5], "user_id": 2},     # List for Bob with Butter and Cheese
    {"list_id": 3, "items": [2, 3, 5], "user_id": 3}    # List for Charlie with Bread, Eggs, and Cheese
]

list_collection.insert_many(lists)
print("Lists added.")


