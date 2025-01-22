const express = require("express");
const router = express.Router();
const {
	getUsers,
	getUser,
	register,
	updateUser,
	deleteUser,
	login,
	logout,
} = require("../controllers/User/User.controller");
const authorized = require("../middleware/authorized");
const isAdmin = require("../middleware/isAdmin");

router.get("/", isAdmin, getUsers);              // Get all users
router.get("/:id", authorized, getUser);         // Get specific user by provided ID
router.put("/:id", authorized, updateUser);      // Update user by provided ID
router.delete("/:id", authorized, deleteUser);   // Delete user by provided ID
router.post("/register", register);   					  // Register a new user
router.post("/login", login);   					    		// Login a user
router.post("/logout", logout);   					   		// Logout a user


module.exports = router;
