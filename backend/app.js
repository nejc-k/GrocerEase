require("dotenv").config();
const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const cors = require("cors");
const authRoutes = require("./routes/User.routes");
const articleRoutes = require("./routes/Article.routes");
const storeRoutes = require("./routes/Store.routes");

const app = express();

const MONGODB_URI = process.env.MONGODB_URI;
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());
app.use(cors());

app.use("/api/user", authRoutes);
app.use("/api/articles", articleRoutes);
app.use("/api/store", storeRoutes);

mongoose.connect(MONGODB_URI, { useNewUrlParser: true, useUnifiedTopology: true })
	.then(() => console.log("MongoDB connected"))
	.catch(err => console.error("MongoDB connection error:", err));


app.listen(PORT, () => console.log(`Server running on port ${PORT}`));