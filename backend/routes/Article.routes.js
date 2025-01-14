const express = require("express");
const router = express.Router();
const {
	getArticles,
	getArticle,
	updateArticle,
	deleteArticle,
	createArticle, getArticlesFromCategory, getArticlesFromStore,
} = require("../controllers/Article.controller");


router.get("/", getArticles);
router.get("/:id", getArticle);
router.get("/store/:id", getArticlesFromStore);
router.get("/category/:category", getArticlesFromCategory);
router.post("/", createArticle);
router.put("/:id", updateArticle);
router.delete("/:id", deleteArticle);

module.exports = router;