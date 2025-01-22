const mongoose = require("mongoose");

const articleSchema = new mongoose.Schema({
	title: { type: String, required: true, unique: true },
	price: { type: Number, required: true },
	imageURL: { type: String, required: false },
	discount: { type: Number, required: false },
	added: { type: Date, default: Date.now },
	updated: { type: Date, default: Date.now },
	category: { type: String, required: true },
	store: { type: String, required: true },
	// store: { type: mongoose.Schema.Types.ObjectId, ref: "Store", required: true },
});

module.exports = mongoose.model("Article", articleSchema);