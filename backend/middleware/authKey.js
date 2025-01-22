require("dotenv").config();

const authKey = (req, res, next) => {
	const authHeader = req.headers["authorization"];
	const key = authHeader && authHeader.split(" ")[1];

	if (!key)
		return res.status(401).json({ message: "Unauthorized" });

	if (key !== process.env.API_AUTH_KEY)
		return res.status(403).json({ message: "Forbidden" });

	next();
};

module.exports = authKey;