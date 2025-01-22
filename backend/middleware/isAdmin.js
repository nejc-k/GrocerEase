const { Request, Response } = require("express");
const jwt = require("jsonwebtoken");

/**
 * @description Check if user is admin or not. Use only for routes that require admin access
 * @module middleware/isAdmin
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @param {NextFunction} next - Next function
 * @returns {Promise<void | Response>} - Promise object
 */
const isAdmin = async (req, res, next) => {
	const token = req.headers.authorization;
	if (!token) {
		return res.status(401).json({ message: "Unauthorized access" });
	}

	const decoded = jwt.verify(token, process.env.JWT_SECRET);
	if (!decoded) {
		return res.status(401).json({ message: "Unauthorized access" });
	}

	const user = await User.findById(decoded.userId);
	if (!user) {
		return res.status(404).json({ message: "User not found" });
	}

	if (user.role !== "admin") {
		return res.status(403).json({ message: "Forbidden access" });
	}

	req.user = user;
	next();
};

module.exports = isAdmin;