// models/Item.js
const mongoose = require('mongoose');

const itemSchema = new mongoose.Schema({
  title: { type: String, required: true },
  price: { type: Number, required: true },
  imageURL: { type: String, required: true },
  discount: { type: Number, default: 0 },
  category: { type: String, required: true },
  store: { type: String, required: true },
  added: { type: Date, default: Date.now },
  updated: { type: Date, default: Date.now }
}, { timestamps: true });

module.exports = mongoose.model('Item', itemSchema);
