const { Request, Response } = require("express");
const jwt = require("jsonwebtoken");
const User = require("../models/User/User.model");

/**
 * @description Check if user is authorized
 * @module middleware/authorized
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @param {NextFunction} next - Next function
 * @returns {Promise<void | Response>} - Promise object
 */
const isAuthorized = async (req, res, next) => {
	const headerValue = req.headers.authorization;
	if (!headerValue) {
		return res.status(401).json({ message: "Unauthorized access" });
	}

	const token = headerValue.split(" ")[1];
	const decoded = jwt.verify(token, process.env.JWT_SECRET);
	if (!decoded) {
		return res.status(401).json({ message: "Unauthorized access" });
	}

	const user = await User.findById(decoded.userId);
	if (!user) {
		return res.status(404).json({ message: "User not found" });
	}

	if (req.params.id && req.params.id !== decoded.userId || user.role !== "admin") {
		return res.status(403).json({ message: "Forbidden access" });
	}

	req.user = user;
	next();
};

module.exports = isAuthorized;