const mongoose = require("mongoose");

const storeSchema = new mongoose.Schema({
	name: { type: String, required: true, unique: true },
	brand: { type: String, required: true },
	address: { type: String, required: true },
	location: {
		lat: { type: Number, required: true },
		lng: { type: Number, required: true },
	},
});

exports.Store = mongoose.model("Store", storeSchema);