const express = require('express');
const Item = require('../models/Item');
const router = express.Router();

router.post('', async (req, res) => {
        try{
        		const query = {};
        		console.log(req.body)
        		if (req.body.category) query.category = req.body.category;
        		if (req.body.store) query.store = req.body.store.toLowerCase();
        		if (req.body.max_price) query.price = { $lte: req.body.max_price };
        		if (req.body.min_price) query.price = { $gte: req.body.min_price };
        		if (req.body.title) query.title = { $regex: new RegExp(req.body.title, "i") };
        		//const articles = await Article.find(query);
        		//if (!articles.length)
        		//	return res.status(404).json({ message: "Articles not found" });

        //const items = await Item.find();
        //must still figure out database
        items = [{
                   "_id": {
                     "$oid": "677db696fdb5a9c1c9870263"
                   },
                   "title": "Toast, Ölz, 500 g",
                   "price": 2.35,
                   "imageURL": "https://images.mercatoronline.si/SMALL/00256062.jpg?229a387e0063c642beb485a8ebe1f117",
                   "discount": 0,
                   "category": "breadAndPastry",
                   "store": "mercator",
                   "added": {
                     "$date": "2025-01-07T23:19:50.382Z"
                   },
                   "updated": {
                     "$date": "2025-01-07T23:19:50.382Z"
                   },
                   "__v": 0
                 },
                 {
                   "_id": {
                     "$oid": "677db696fdb5a9c1c9870266"
                   },
                   "title": "Masleni toast, Ölz, 500 g",
                   "price": 3.29,
                   "imageURL": "https://mercator-production-images.s3.eu-central-1.amazonaws.com/productLabel/image/product_label_image_2.png",
                   "discount": 0,
                   "category": "breadAndPastry",
                   "store": "mercator",
                   "added": {
                     "$date": "2025-01-07T23:19:50.417Z"
                   },
                   "updated": {
                     "$date": "2025-01-07T23:19:50.417Z"
                   },
                   "__v": 0
                 }]

        res.status(200).json(items);
        } catch (error) {
            console.log("Error fetching items: ", error.message);
            res.status(500).json({
                success: false,
                message: "Server error"
            });
        }
});


module.exports = router;
