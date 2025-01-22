const mongoose = require("mongoose");
const Article = require("../../models/Article/Article.model");

describe("Article Model Test", () => {
	beforeAll(async () => {
		const url = `mongodb://127.0.0.1/article_model_test_db`;
		await mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true });
	});

	afterAll(async () => {
		await mongoose.connection.db.dropDatabase();
		await mongoose.connection.close();
	});

	beforeEach(async () => {
		await Article.deleteMany({});
	});

	it("should create & save an article successfully", async () => {
		const articleData = { title: "Test Article", content: "This is a test article.", author: "Author Name" };
		const validArticle = new Article(articleData);
		const savedArticle = await validArticle.save();

		expect(savedArticle._id).toBeDefined();
		expect(savedArticle.title).toBe(articleData.title);
		expect(savedArticle.content).toBe(articleData.content);
		expect(savedArticle.author).toBe(articleData.author);
	});

	it("should fail to create an article without required fields", async () => {
		const articleWithoutRequiredField = new Article({ title: "Test Article" });
		let err;
		try {
			await articleWithoutRequiredField.save();
		} catch (error) {
			err = error;
		}
		expect(err).toBeInstanceOf(mongoose.Error.ValidationError);
		expect(err.errors.content).toBeDefined();
		expect(err.errors.author).toBeDefined();
	});

	it("should fail to create an article with duplicate title", async () => {
		const articleData = { title: "Test Article", content: "This is a test article.", author: "Author Name" };
		const article1 = new Article(articleData);
		await article1.save();

		const article2 = new Article(articleData);
		let err;
		try {
			await article2.save();
		} catch (error) {
			err = error;
		}
		expect(err).toBeInstanceOf(mongoose.Error);
		expect(err.code).toBe(11000); // Duplicate key error code
	});
});