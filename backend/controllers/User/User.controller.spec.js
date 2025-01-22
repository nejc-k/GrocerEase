require("dotenv").config();
const request = require("supertest");
const express = require("express");
const mongoose = require("mongoose");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const User = require("../../models/User/User.model");

const app = express();
app.use(express.json());
app.use("/users", require("../../routes/User.routes"));

const TEST_DB_URL = `mongodb://127.0.0.1/user_test_db`;

describe("User Controller", () => {
	let adminToken, userToken, admin, user;

	beforeAll(async () => {
		await mongoose.connect(TEST_DB_URL);
		await mongoose.connection.db.dropDatabase();
	});

	afterAll(async () => {
		await mongoose.connection.db.dropDatabase();
		await mongoose.connection.close();
	});

	beforeEach(async () => {
		await User.deleteMany({});

		const hashedPassword = await bcrypt.hash("password123", 10);

		admin = await User.create({
			username: "Admin User",
			email: "admin@example.com",
			password: hashedPassword,
			role: "admin",
		});

		user = await User.create({
			username: "Regular User",
			email: "user@example.com",
			password: hashedPassword,
		});

		adminToken = jwt.sign({ userId: admin._id }, process.env.JWT_SECRET, { expiresIn: "24h" });
		userToken = jwt.sign({ userId: user._id }, process.env.JWT_SECRET, { expiresIn: "24h" });
	});

	describe("GET /users", () => {
		it("should return 401 if a non-admin user attempts to access all users", async () => {
			const res = await request(app)
				.get("/users")
				.set("Authorization", `Bearer ${userToken}`);
			expect(res.status).toBe(401);
			expect(res.body.message).toBe("Unauthorized access");
		});

		it("should return 200 and all users for an admin user", async () => {
			const res = await request(app)
				.get("/users")
				.set("Authorization", `Bearer ${adminToken}`);
			expect(res.status).toBe(200);
			expect(res.body).toHaveLength(2);
			expect(res.body[0].username).toBe("Admin User");
		});
	});

	describe("GET /users/:id", () => {
		it("should allow a user to access their own profile", async () => {
			const res = await request(app)
				.get(`/users/${user._id}`)
				.set("Authorization", `Bearer ${userToken}`);
			expect(res.status).toBe(200);
			expect(res.body.username).toBe("Regular User");
		});

		it("should allow an admin to access any user profile", async () => {
			const res = await request(app)
				.get(`/users/${user._id}`)
				.set("Authorization", `Bearer ${adminToken}`);
			expect(res.status).toBe(200);
			expect(res.body.username).toBe("Regular User");
		});
	});

	describe("POST /register", () => {
		it("should create a new user", async () => {
			const newUser = { username: "New User", email: "newuser@example.com", password: "password123" };

			const res = await request(app)
				.post("/users/register")
				.send(newUser);
			expect(res.status).toBe(201);
			expect(res.body.message).toBe("User registered successfully");
		});
	});

	describe("PUT /users/:id", () => {
		it("should allow a user to update their profile", async () => {
			const updatedData = { username: "Updated User" };

			const res = await request(app)
				.put(`/users/${user._id}`)
				.set("Authorization", `Bearer ${userToken}`)
				.send(updatedData);
			expect(res.status).toBe(200);
			expect(res.body.username).toBe("Updated User");
		});

		it("should return 403 if a user tries to update another user’s profile", async () => {
			const updatedData = { username: "Hacked Profile" };

			const res = await request(app)
				.put(`/users/${admin._id}`)
				.set("Authorization", `Bearer ${userToken}`)
				.send(updatedData);
			expect(res.status).toBe(403);
		});
	});

	describe("DELETE /users/:id", () => {
		it("should allow an admin to delete any user", async () => {
			const res = await request(app)
				.delete(`/users/${user._id}`)
				.set("Authorization", `Bearer ${adminToken}`);
			expect(res.status).toBe(200);
			expect(res.body._id).toBe(user._id.toString());
		});

		it("should return 403 if a regular user attempts to delete another user", async () => {
			const res = await request(app)
				.delete(`/users/${admin._id}`)
				.set("Authorization", `Bearer ${userToken}`);
			expect(res.status).toBe(403);
		});
	});
});