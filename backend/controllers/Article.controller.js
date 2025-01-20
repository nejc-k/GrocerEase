const { Request, Response } = require("express");
const Article = require("../models/Article.model");

/**
 * @description Get all articles from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.getArticles = async (req, res) => {
	try {
		const articles = await Article.find();
		if (!articles.length) {
			return res.status(404).json({ message: "Articles not found" });
		}
		res.status(200).json(articles);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Get all articles from the database for the specified store
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.getArticlesFromStore = async (req, res) => {
	try {
		const articles = await Article.find({ category: req.params.id });
		if (!articles.length) {
			return res.status(404).json({ message: "Articles not found" });
		}
		res.status(200).json(articles);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Get all articles from the database for the specified category
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.getArticlesFromCategory = async (req, res) => {
	try {
		const articles = await Article.find({ category: req.params.category });
		if (!articles.length) {
			return res.status(404).json({ message: "Articles not found" });
		}
		res.status(200).json(articles);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Query articles by parameters given in the request body. This function is used to filter articles by
 * 							category, store, price, and title in any combination of those specified parameters. If none are
 * 							specified, all articles are returned and functions acts like getArticles function.
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 * @example {
 * 	"category": "breadAndPastry",
 * 	"store": "Mercator",
 * 	"max_price": 0.50,
 * 	"min_price": 2.49,
 * 	"title": "Žemlja"
 * }
 * */
exports.queryArticles = async (req, res) => {
	try {
		const query = {};
		if (req.body.category) query.category = req.body.category;
		if (req.body.store) query.store = req.body.store.toLowerCase();
		if (req.body.max_price) query.price = { $lte: req.body.max_price };
		if (req.body.min_price) query.price = { $gte: req.body.min_price };
		if (req.body.title) query.title = { $regex: new RegExp(req.body.title, "i") };
		 
		const articles = await Article.find(query);
		if (!articles.length)
			return res.status(404).json({ message: "Articles not found" });

		res.status(200).json(articles);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Get a single article from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.getArticle = async (req, res) => {
	try {
		const article = await Article.findById(req.params.id);
		if (!article) {
			return res.status(404).json({ message: "Article not found" });
		}
		res.status(200).json(article);
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Create an article in the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.createArticle = async (req, res) => {
	try {
		if (!req.body) {
			return res.status(400).json({ message: "Request body is empty" });
		}

		const existingArticle = await Article.findOne({ title: req.body.title });
		if (existingArticle) {
			console.warn("Article already exists, updating it");
			req.params.id = existingArticle._id;
			return await exports.updateArticle(req, res);
			// return res.status(409).json({ message: "Article already exists" });
		}

		const article = await Article.create({ ...req.body });
		res.status(201).json({ article, message: "Article created successfully" });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Update an article in the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void | Response>} - Promise object
 */
exports.updateArticle = async (req, res) => {
	try {
		const updatedArticle = await Article.findByIdAndUpdate(
			req.params.id,
			{ ...req.body },
			{ new: true },
		);

		if (!updatedArticle) {
			return res.status(404).json({ message: "Article not found" });
		}

		res.status(200).json({ article: updatedArticle, message: "Article updated successfully" });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};

/**
 * @description Delete an article from the database
 * @param {Request} req - Request object
 * @param {Response} res - Response object
 * @returns {Promise<void>} - Promise object
 */
exports.deleteArticle = async (req, res) => {
	try {
		const article = await Article.findById(req.params.id);
		if (!article) {
			return res.status(404).json({ error: "Article not found" });
		}

		await article.remove();
		res.status(204).json({ message: "Article deleted successfully" });
	} catch (error) {
		res.status(500).json({ message: "Server error", error });
	}
};