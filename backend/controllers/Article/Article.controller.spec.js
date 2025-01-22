const request = require("supertest");
const express = require("express");
const mongoose = require("mongoose");
const Article = require("../../models/Article/Article.model");

const app = express();
app.use(express.json());
app.use("/article", require("../../routes/Article.routes"));

describe("Article Controller", () => {
	beforeAll(async () => {
		const url = `mongodb://127.0.0.1/article_test_db`;
		await mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true });
	});

	afterAll(async () => {
		await mongoose.connection.close();
	});

	beforeEach(async () => {
		await Article.deleteMany({});
	});

	describe("GET /article", () => {
		it("should get all articles", async () => {
			const articles = [
				{ title: "Article 1", category: "Category 1", store: "Store 1", price: 1.00, discount: 0.00 },
				{ title: "Article 2", category: "Category 2", store: "Store 2", price: 2.00, discount: 0.00 },
			];
			await Article.insertMany(articles);

			const res = await request(app).get("/article");
			expect(res.status).toBe(200);
			expect(res.body.length).toBe(2);
		});
	});

	describe("GET /article/:id", () => {
		it("should get a specific article by ID", async () => {
			const article = new Article({
				title: "Article 1",
				category: "Category 1",
				store: "Store 1",
				price: 1.00,
				discount: 0.00,
			});
			await article.save();

			const res = await request(app).get(`/article/${article._id}`);
			expect(res.status).toBe(200);
			expect(res.body.title).toBe("Article 1");
		});
	});

	describe("GET /article/store/:id", () => {
		it("should get all articles from the specified store", async () => {
			const articles = [
				{ title: "Article 1", category: "Category 1", store: "Store 1", price: 1.00, discount: 0.00 },
				{ title: "Article 2", category: "Category 2", store: "Store 2", price: 2.00, discount: 0.00 },
				{ title: "Article 3", category: "Category 2", store: "Store 1", price: 3.00, discount: 0.00 },
			];
			await Article.insertMany(articles);

			const res = await request(app).get("/article/store/Store 1");
			expect(res.status).toBe(200);
			expect(res.body.length).toBe(2);
		});
	});

	describe("GET /article/category/:category", () => {
		it("should get all articles from the specified category", async () => {
			const articles = [
				{ title: "Article 1", category: "Category 1", store: "Store 1", price: 1.00, discount: 0.00 },
				{ title: "Article 2", category: "Category 1", store: "Store 1", price: 2.00, discount: 0.00 },
			];
			await Article.insertMany(articles);

			const res = await request(app).get("/article/category/Category 1");
			expect(res.status).toBe(200);
			expect(res.body.length).toBe(2);
		});
	});

	describe("POST /article/query", () => {
		it("should query articles by parameters given in the request body", async () => {
			const articles = [
				{ title: "Article 1", category: "Category 1", store: "Store 1", price: 1.00, discount: 0.00 },
				{ title: "Article 2", category: "Category 2", store: "Store 2", price: 2.00, discount: 0.00 },
				{ title: "Article 3", category: "Category 1", store: "Store 2", price: 3.00, discount: 0.00 },
			];
			await Article.insertMany(articles);

			const res = await request(app)
				.post("/article/query")
				.send({ category: "Category 1", store: "Store 1" });
			expect(res.status).toBe(200);
			expect(res.body.length).toBe(1);
		});
	});

	describe("POST /article", () => {
		it("should create a new article", async () => {
			const article = { title: "Article 1", category: "Category 1", store: "Store 1", price: 1.00, discount: 0.00 };

			const res = await request(app)
				.post("/article")
				.set("Authorization", `Bearer ${process.env.API_AUTH_KEY}`)
				.send(article);
			expect(res.status).toBe(201);
			expect(res.body.article.title).toBe("Article 1");
		});
	});

	describe("PUT /article/:id", () => {
		it("should update an article by ID", async () => {
			const article = new Article({
				title: "Article 1",
				category: "Category 1",
				store: "Store 1",
				price: 1.00,
				discount: 0.00,
			});
			await article.save();

			const updatedArticle = {
				title: "Updated Article 1",
				category: "Category Updated",
				store: "Store Updated",
				price: 1.00,
				discount: 0.00,
			};

			const res = await request(app)
				.put(`/article/${article._id}`)
				.set("Authorization", `Bearer ${process.env.API_AUTH_KEY}`)
				.send(updatedArticle);
			expect(res.status).toBe(200);
			expect(res.body.article.title).toBe("Updated Article 1");
		});
	});

	describe("DELETE /article/:id", () => {
		it("should delete an article by ID", async () => {
			const article = new Article({
				title: "Article 1",
				category: "Category 1",
				store: "Store 1",
				price: 1.00,
				discount: 0.00,
			});
			await article.save();

			const res = await request(app)
				.delete(`/article/${article._id}`)
				.set("Authorization", `Bearer ${process.env.API_AUTH_KEY}`);
			expect(res.status).toBe(204);
		});
	});
});