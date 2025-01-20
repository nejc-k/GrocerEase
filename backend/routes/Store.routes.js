const express = require("express");
const { getStores, getStore, createStore, updateStore, deleteStore } = require("../controllers/Store.controller");
const router = express.Router();

router.get("/", getStores);						// Get all stores
router.get("/:id", getStore);					// Get specific store by provided ID
router.post("/", createStore);					// Create a new store
router.put("/:id", updateStore);				// Update a store by provided ID
router.delete("/:id", deleteStore);		// Delete a store by provided ID

module.exports = router;