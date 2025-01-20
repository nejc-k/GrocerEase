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
	.then(() => console.log("MongoDB connected"))
	.catch(err => console.error("MongoDB connection error:", err));


try {
	const sslOptions = {
		key: fs.readFileSync(process.env.PRIVATE_KEY_PATH),
		cert: fs.readFileSync(process.env.CERTIFICATE_PATH),
	};

	https.createServer(sslOptions, app).listen(443, () => {
		console.log("HTTPS server running on port 443");
	});

	http.createServer((req, res) => {
		res.writeHead(301, { Location: `https://${req.headers.host}${req.url}` });
		res.end();
	}).listen(80, () => {
		console.log("HTTP server redirecting to HTTPS on port 80");
	});
} catch (error) {
	console.error("[ERROR]: HTTPS server error:", error);
	console.info("[INFO]: Starting HTTP server on port 3000");
	app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
}
