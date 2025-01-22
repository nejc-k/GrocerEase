// models/User.model.js
const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
	username: { type: String, required: true, unique: true },
	email: { type: String, required: true, unique: true },
	password: { type: String, required: true },
	profile_image: { type: String }, // Store URI or file path
	role: { type: String, default: "user" },
});

module.exports = mongoose.model("User", userSchema);
