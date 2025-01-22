const mongoose = require("mongoose");
const User = require("../../models/User/User.model");

describe("User Model Test", () => {
	beforeAll(async () => {
		const url = `mongodb://127.0.0.1/user_model_test_db`;
		await mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true });
	});

	afterAll(async () => {
		await mongoose.connection.db.dropDatabase();
		await mongoose.connection.close();
	});

	beforeEach(async () => {
		await User.deleteMany({});
	});

	it("should create & save a user successfully", async () => {
		const userData = { username: "testuser", email: "testuser@example.com", password: "password123" };
		const validUser = new User(userData);
		const savedUser = await validUser.save();

		expect(savedUser._id).toBeDefined();
		expect(savedUser.username).toBe(userData.username);
		expect(savedUser.email).toBe(userData.email);
		expect(savedUser.password).toBe(userData.password);
	});

	it("should fail to create a user without required fields", async () => {
		const userWithoutRequiredField = new User({ username: "testuser" });
		let err;
		try {
			await userWithoutRequiredField.save();
		} catch (error) {
			err = error;
		}
		expect(err).toBeInstanceOf(mongoose.Error.ValidationError);
		expect(err.errors.email).toBeDefined();
		expect(err.errors.password).toBeDefined();
	});

	it("should fail to create a user with duplicate email", async () => {
		const userData = { username: "testuser", email: "testuser@example.com", password: "password123" };
		const user1 = new User(userData);
		await user1.save();

		const user2 = new User(userData);
		let err;
		try {
			await user2.save();
		} catch (error) {
			err = error;
		}
		expect(err).toBeInstanceOf(mongoose.Error);
		expect(err.code).toBe(11000); // Duplicate key error code
	});
});