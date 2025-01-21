const { Request, Response } = require("express");
const Store = require("../../models/Store.model");

/**
 * @description Get all stores from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.getStores = async (req, res) => {
	try {
		const stores = await Store.find();
		if (!stores.length) {
			return res.status(404).json({ message: "Stores not found" });
		}
		res.status(200).json(stores);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Get a single store from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.getStore = async (req, res) => {
	try {
		const store = await Store.findById(req.params.id);
		if (!store) {
			return res.status(404).json({ message: "Store not found" });
		}
		res.status(200).json(store);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Create a new store in the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.createStore = async (req, res) => {
	try {
		const store = await Store.create(req.body);
		res.status(201).json(store);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Update a store in the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.updateStore = async (req, res) => {
	try {
		const store = await Store.findByIdAndUpdate(req.params.id, req.body, { new: true });
		if (!store) {
			return res.status(404).json({ message: "Store not found" });
		}

		res.status(200).json(store);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Delete a store from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.deleteStore = async (req, res) => {
	try {
		const store = await Store.findByIdAndDelete(req.params.id);
		if (!store) {
			return res.status(404).json({ message: "Store not found" });
		}
		res.status(200).json({ message: "Store deleted successfully" });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};