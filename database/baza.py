from pymongo import MongoClient
import bcrypt

# MongoDB URI
uri = "mongodb+srv://user:IigChwsYtIpq8R21@cluster0.o50mfr6.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
client = MongoClient(uri)

# Database name
db = client['grocerease']

### Insert data into the collections
## Items
items_collection = db['items']
items = [
    {"id_itema": 1, "trgovina": "Mercator", "st_izdelkov": 2, "ime": "Milk", "note": "Buy low-fat"},
    {"id_itema": 2, "trgovina": "Spar", "st_izdelkov": 1, "ime": "Bread", "note": "Whole wheat"},
    {"id_itema": 3, "trgovina": "Lidl", "st_izdelkov": 12, "ime": "Eggs", "note": "Free range"},
    {"id_itema": 4, "trgovina": "Hofer", "st_izdelkov": 1, "ime": "Butter", "note": "Unsalted"},
    {"id_itema": 5, "trgovina": "Tuš", "st_izdelkov": 3, "ime": "Cheese", "note": "Mozzarella"}
]
items_collection.insert_many(items)

## Users
user_collection = db['users']
def hash_password(password):
    salt = bcrypt.gensalt() 
    hashed = bcrypt.hashpw(password.encode('utf-8'), salt)  
    return hashed

users = [
    {"user_id": 1, "ime": "Alice", "email": "alice@example.com", "password": hash_password("securePassword1").decode("utf-8")},
    {"user_id": 2, "ime": "Bob", "email": "bob@example.com", "password": hash_password("securePassword2").decode("utf-8")},
    {"user_id": 3, "ime": "Charlie", "email": "charlie@example.com", "password": hash_password("securePassword3").decode("utf-8")}
]

user_collection.insert_many(users)
print("Users with hashed passwords and IDs added.")

## Lists
list_collection = db['lists']
lists = [
    {"list_id": 1, "user_id": 1, "itemi": [1, 2, 3]},  
    {"list_id": 2, "user_id": 1, "itemi": [4, 5]},     
    {"list_id": 3, "user_id": 1, "itemi": [2, 3, 5]}   
    {"list_id": 4, "user_id": 2, "itemi": [1, 5]},    
    {"list_id": 5, "user_id": 3, "itemi": [1, 2, 3, 5]}  
]

list_collection.insert_many(lists)
print("Lists with user IDs added.")
