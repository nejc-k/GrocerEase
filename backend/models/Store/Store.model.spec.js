const mongoose = require("mongoose");
const Store = require("../../models/Store/Store.model");

describe("Store Model Test", () => {
	beforeAll(async () => {
		const url = `mongodb://127.0.0.1/store_model_test_db`;
		await mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true });
	});

	afterAll(async () => {
		await mongoose.connection.db.dropDatabase();
		await mongoose.connection.close();
	});

	beforeEach(async () => {
		await Store.deleteMany({});
	});

	it("should create & save a store successfully", async () => {
		const storeData = { name: "Test Store", location: "Test Location" };
		const validStore = new Store(storeData);
		const savedStore = await validStore.save();

		expect(savedStore._id).toBeDefined();
		expect(savedStore.name).toBe(storeData.name);
		expect(savedStore.location).toBe(storeData.location);
	});

	it("should fail to create a store without required fields", async () => {
		const storeWithoutRequiredField = new Store({ name: "Test Store" });
		let err;
		try {
			await storeWithoutRequiredField.save();
		} catch (error) {
			err = error;
		}
		expect(err).toBeInstanceOf(mongoose.Error.ValidationError);
		expect(err.errors.location).toBeDefined();
	});

	it("should fail to create a store with duplicate name", async () => {
		const storeData = { name: "Test Store", location: "Test Location" };
		const store1 = new Store(storeData);
		await store1.save();

		const store2 = new Store(storeData);
		let err;
		try {
			await store2.save();
		} catch (error) {
			err = error;
		}
		expect(err).toBeInstanceOf(mongoose.Error);
		expect(err.code).toBe(11000); // Duplicate key error code
	});
});