const request = require("supertest");
const express = require("express");
const mongoose = require("mongoose");
const Article = require("../../models/Article.model");
const articleRoutes = require("../../routes/Article.routes");
const app = express();

app.use(express.json());
app.use("/api/articles", articleRoutes);

jest.mock("../../models/Article.model");

describe("Article Controller", () => {
	beforeAll(async () => {
		await mongoose.connect("mongodb://localhost:27017/test", {
			useNewUrlParser: true,
			useUnifiedTopology: true,
		});
	});

	afterAll(async () => {
		await mongoose.connection.close();
	});

	it("should get all articles", async () => {
		Article.find.mockResolvedValue([{ title: "Test Article" }]);
		const res = await request(app).get("/api/articles");
		expect(res.statusCode).toEqual(200);
		expect(res.body).toHaveLength(1);
		expect(res.body[0].title).toBe("Test Article");
	});

	it("should get a single article by ID", async () => {
		const articleId = new mongoose.Types.ObjectId();
		Article.findById.mockResolvedValue({ _id: articleId, title: "Test Article" });
		const res = await request(app).get(`/api/articles/${articleId}`);
		expect(res.statusCode).toEqual(200);
		expect(res.body.title).toBe("Test Article");
	});

	it("should create a new article", async () => {
		const newArticle = { title: "New Article" };
		Article.create.mockResolvedValue(newArticle);
		const res = await request(app).post("/api/articles").send(newArticle);
		expect(res.statusCode).toEqual(201);
		expect(res.body.article.title).toBe("New Article");
	});

	it("should update an article by ID", async () => {
		const articleId = new mongoose.Types.ObjectId();
		const updatedArticle = { title: "Updated Article" };
		Article.findByIdAndUpdate.mockResolvedValue(updatedArticle);
		const res = await request(app).put(`/api/articles/${articleId}`).send(updatedArticle);
		expect(res.statusCode).toEqual(200);
		expect(res.body.article.title).toBe("Updated Article");
	});

	it("should delete an article by ID", async () => {
		const articleId = new mongoose.Types.ObjectId();
		Article.findById.mockResolvedValue({ _id: articleId, title: "Test Article" });
		Article.findByIdAndRemove.mockResolvedValue(null);
		const res = await request(app).delete(`/api/articles/${articleId}`);
		expect(res.statusCode).toEqual(204);
	});
});