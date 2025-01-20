require("dotenv").config();
const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
const authRoutes = require("./routes/User.routes");
const articleRoutes = require("./routes/Article.routes");
const storeRoutes = require("./routes/Store.routes");
const path = require("node:path");
const fs = require("fs");
const https = require("https");
const http = require("http");

const app = express();

const MONGODB_URI = process.env.MONGODB_URI;
const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());

app.use("/api/user", authRoutes);
app.use("/api/article", articleRoutes);
app.use("/api/store", storeRoutes);

// Default response with a welcome message and brief description of the API if user accessed the root URL
app.get("/", (req, res) => {
	res.sendFile(path.join(__dirname, "views", "index.html"));
});

mongoose.connect(MONGODB_URI, { useNewUrlParser: true, useUnifiedTopology: true })
	.then(() => console.log("[INFO] MongoDB connected"))
	.catch(err => console.error("[ERROR] MongoDB connection error:", err));


app.listen(PORT, () => console.log(`[INFO] Server running on port ${PORT}`));
