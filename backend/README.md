# Backend Documentation

## Table of Contents

- [Introduction](#introduction)
- [Installation](#installation)
- [Folder Structure](#folder-structure)
- [Environment Variables](#environment-variables)
- [Available Scripts](#available-scripts)
- [API Endpoints](#api-endpoints)
- [Controllers](#controllers)
- [Models](#models)
- [Middlewares](#middlewares)

## Introduction

This is the backend part of the project, built using Node.js and Express. It provides RESTful API endpoints for managing
articles, stores, and user authentication.

## Installation

To install the dependencies, run the following command:

```sh
npm install
```

To start the server, run the following command:

```sh
npm run dev
```

## Folder Structure

The folder structure is as follows:

```
/backend
├── controllers
│   ├── Article.controller.js
│   ├── Store.controller.js
│   └── User.controller.js
├── models
│   ├── Article.model.js
│   ├── Store.model.js
│   └── User.model.js
├── routes
│   ├── Article.routes.js
│   ├── Store.routes.js
│   └── User.routes.js
├── middlewares
│   └── auth.middleware.js
├── app.js
└── server.js
```

## Environment Variables

The following environment variables are required:

- `PORT`: Port number for the server
    - Example: `3000`


- `MONGO_URI`: MongoDB connection string
    - Example: `mongodb://localhost:27017`


- `JWT_SECRET`
    - Example: `your-jwt-secret`

## Available Scripts

In the project directory, you can run:

```sh
npm run dev
```

Runs the app in the development mode:

```sh
npm start
```

Runs unit tests:

```sh
npm test
```

## API Endpoints

The following API endpoints are available:

- `/api/articles`

    - `GET /articles`: Get all articles.
    - `GET /articles/:id`: Get a single article by ID.
    - `POST /articles`: Create a new article.
    - `PUT /articles/:id`: Update an article by ID.
    - `DELETE /articles/:id`: Delete an article by ID.
    - `GET /articles/store/:id`: Get articles from a specific store.
    - `GET /articles/category/:category`: Get articles from a specific category.
    - `POST /articles/query`: Query articles by parameters.


- `/api/stores`

    - `GET /stores`: Get all stores
    - `GET /stores/:id`: Get a store by ID
    - `POST /stores`: Create a new store
    - `PUT /stores/:id`: Update a store by ID
    - `DELETE /stores/:id`: Delete a store by ID


- `/api/users`

    - `GET /user/:id`: Get a user by ID
    - `GET /users/`: Get all users
    - `POST /users/register`: Register a new user
    - `POST /users/login`: Login a user
    - `POST /users/logout`: Logout the current user
    - `PUT /users/:id`: Update a user by ID
    - `DELETE /users/:id`: Delete a user by ID

## Controllers

The controllers are responsible for handling the requests and responses. They interact with the models to perform CRUD
operations.

- `Article.controller.js`:

    - `getArticles`: Get all articles from the database.
    - `getArticle`: Get a single article from the database.
    - `createArticle`: Create an article in the database.
    - `updateArticle`: Update an article in the database.
    - `deleteArticle`: Delete an article from the database.
    - `getArticlesFromStore`: Get all articles from the database for the specified store.
    - `getArticlesFromCategory`: Get all articles from the database for the specified category.
    - `queryArticles`: Query articles by parameters given in the request body.


- `Store.controller.js`:

    - `getStores`: Get all stores from the database.
    - `getStore`: Get a single store from the database.
    - `createStore`: Create a store in the database.
    - `updateStore`: Update a store in the database.
    - `deleteStore`: Delete a store from the database.


- `User.controller.js`:

    - `getUser`: Get a single user from the database.
    - `getUsers`: Get all users from the database.
    - `registerUser`: Register a new user in the database.
    - `loginUser`: Login a user.
    - `logoutUser`: Logout the current user.
    - `updateUser`: Update a user in the database.
    - `deleteUser`: Delete a user from the database.

## Models

The models are responsible for defining the schema for the collections in the database.

- `Article.model.js`: Model for articles

    - `title`: Title of the article
    - `description`: Description of the article
    - `category`: Category of the article
    - `price`: Price of the article
    - `store`: Store ID of the article


- `Store.model.js`: Model for stores

    - `name`: Name of the store
    - `description`: Description of the store
    - `location`: Location of the store
    - `articles`: Articles in the store


- `User.model.js`: Model for users

    - `name`: Name of the user
    - `email`: Email of the user
    - `password`: Password of the user

## Middlewares

The middlewares are responsible for handling the requests before they reach the controllers.

- `auth.middleware.js`: Middleware for user authentication

    - `authenticate`: Authenticate the user using JWT
    - `authorize`: Authorize the user based on the role
