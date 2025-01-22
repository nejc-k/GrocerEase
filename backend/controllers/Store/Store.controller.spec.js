const request = require("supertest");
const express = require("express");
const mongoose = require("mongoose");
const Store = require("../../models/Store/Store.model");

const app = express();
app.use(express.json());
app.use("/stores", require("../../routes/Store.routes"));

describe("Store Controller", () => {
	beforeAll(async () => {
		const url = `mongodb://127.0.0.1/store_test_db`;
		await mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true });
	});

	afterAll(async () => {
		await mongoose.connection.close();
	});

	beforeEach(async () => {
		await Store.deleteMany({});
	});

	describe("GET /stores", () => {
		it("should get all stores", async () => {
			const stores = [
				{ name: "Store 1", location: "Location 1" },
				{ name: "Store 2", location: "Location 2" },
			];
			await Store.insertMany(stores);

			const res = await request(app).get("/stores");
			expect(res.status).toBe(200);
			expect(res.body.length).toBe(2);
		});
	});

	describe("GET /stores/:id", () => {
		it("should get a specific store by ID", async () => {
			const store = new Store({ name: "Store 1", location: "Location 1" });
			await store.save();

			const res = await request(app).get(`/stores/${store._id}`);
			expect(res.status).toBe(200);
			expect(res.body.name).toBe("Store 1");
		});
	});

	describe("POST /stores", () => {
		it("should create a new store", async () => {
			const store = { name: "Store 1", location: "Location 1" };

			const res = await request(app)
				.post("/stores")
				.send(store);
			expect(res.status).toBe(201);
			expect(res.body.name).toBe("Store 1");
		});
	});

	describe("PUT /stores/:id", () => {
		it("should update a store by ID", async () => {
			const store = new Store({ name: "Store 1", location: "Location 1" });
			await store.save();

			const updatedStore = { name: "Updated Store 1", location: "Updated Location 1" };

			const res = await request(app)
				.put(`/stores/${store._id}`)
				.send(updatedStore);
			expect(res.status).toBe(200);
			expect(res.body.name).toBe("Updated Store 1");
		});
	});

	describe("DELETE /stores/:id", () => {
		it("should delete a store by ID", async () => {
			const store = new Store({ name: "Store 1", location: "Location 1" });
			await store.save();

			const res = await request(app)
				.delete(`/stores/${store._id}`);
			expect(res.status).toBe(200);
			expect(res.body.message).toBe("Store deleted successfully");
		});
	});
});