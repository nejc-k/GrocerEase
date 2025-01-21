const express = require("express");
const router = express.Router();
const {
	getArticles,
	getArticle,
	updateArticle,
	deleteArticle,
	createArticle, getArticlesFromCategory, getArticlesFromStore, queryArticles,
} = require("../controllers/Article/Article.controller");


router.get("/", getArticles);																// Get all articles
router.get("/:id", getArticle);															// Get specific article by provided ID
router.get("/store/:id", getArticlesFromStore);							// Get all articles from the specified store
router.get("/category/:category", getArticlesFromCategory);	// Get all articles from the specified category
router.post("/query", queryArticles);												// Query articles by parameters given in the request body
router.post("/", createArticle);															// Create a new article
router.put("/:id", updateArticle);														// Update an article by provided ID
router.delete("/:id", deleteArticle);												// Delete an article by provided ID

module.exports = router;