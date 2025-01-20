const { Request, Response } = require("express");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const User = require("../../models/User.model");

const JWT_SECRET = process.env.JWT_SECRET;

/**
 * @description Get all users from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 * */
exports.getUsers = async (req, res) => {
	try {
		const token = req.headers["authorization"]?.split(" ")[1];

		if (!token)
			return res.status(401).json({ message: "Unauthorized access" });

		const decoded = jwt.verify(token, JWT_SECRET);
		if (!decoded)
			return res.status(401).json({ message: "Unauthorized access" });

		const user = await User.findById(decoded.userId);
		if (!user || !user.isAdmin)
			return res.status(401).json({ message: "Unauthorized access" });

		const users = await User.find();
		if (!users.length) {
			return res.status(404).json({ message: "Users not found" });
		}
		res.status(200).json(users);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Get a single user from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @return {Promise<void | Response>} - Promise object
 */
exports.getUser = async (req, res) => {
	try {
		const token = req.headers["authorization"]?.split(" ")[1];

		if (!token)
			return res.status(401).json({ message: "Unauthorized access" });

		const decoded = jwt.verify(token, JWT_SECRET);
		const user = await User.findById(decoded.userId).select("-password");

		if (!user)
			return res.status(404).json({ message: "User not found" });

		res.status(200).json(user);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Update a user in the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @return {Promise<void | Response>}
 */
exports.updateUser = async (req, res) => {
	try {
		const user = await User.findByIdAndUpdate(req.params.id, req.body, { new: true });
		if (!user) {
			return res.status(404).json({ message: "User not found" });
		}
		res.status(200).json(user);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

exports.deleteUser = async (req, res) => {
	try {
		const user = await User.findByIdAndDelete(req.params.id);
		if (!user) {
			return res.status(404).json({ message: "User not found" });
		}
		res.status(200).json(user);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Register a new user
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.register = async (req, res) => {
	try {
		const { username, email, password, profile_image } = req.body;

		if (!username || !email || !password) {
			return res.status(400).json({ message: "Please fill in all fields" });
		}

		const existingUser = await User.findOne({ email });
		if (existingUser) {
			return res.status(400).json({ message: "User already exists" });
		}

		const hashedPassword = await bcrypt.hash(password, 10);

		const user = new User({
			username,
			email,
			password: hashedPassword,
			profile_image,
		});

		await user.save();
		res.status(201).json({ message: "User registered successfully" });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Login a user
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.login = async (req, res) => {
	try {
		const { email, password } = req.body;

		if (!email || !password)
			return res.status(400).json({ message: "Please fill in all fields" });

		const user = await User.findOne({ email });
		if (!user)
			return res.status(400).json({ message: "Invalid email or password" });

		const isMatch = await bcrypt.compare(password, user.password);
		if (!isMatch)
			return res.status(400).json({ message: "Invalid email or password" });

		const token = jwt.sign({ userId: user._id }, JWT_SECRET, { expiresIn: "24h" });
		res.status(200).json({ message: "Login successful", token, userId: user._id });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Logout a user
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.logout = async (req, res) => {
	try {
		// Implement logout logic if needed (e.g., token invalidation)
		res.status(200).json({ message: "Logout successful" });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};