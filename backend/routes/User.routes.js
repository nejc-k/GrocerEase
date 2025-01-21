const express = require("express");
const router = express.Router();
const { getUsers, getUser, register } = require("../controllers/User/User.controller");

router.get("/", getUsers);              // Get all users
router.get("/:id", getUser);            // Get specific user by provided ID
router.put("/:id", getUser);            // Update user by provided ID
router.delete("/:id", getUser);         // Delete user by provided ID
router.post("/", register);             // Create a new user
router.post("/register", register);     // Register a new user
router.post("/login", register);        // Login a user
router.post("/logout", register);       // Logout a user


module.exports = router;
