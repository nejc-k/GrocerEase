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
        items = [
                    {
                        "_id": "6786dfbc3eceb78881775c85",
                        "title": "Toast, Ölz, 500 g",
                        "price": 2.35,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00256062.jpg?c35384b79f6ef73cd1995904fc501395",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.126Z",
                        "updated": "2025-01-14T22:05:48.126Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c88",
                        "title": "Masleni toast, Ölz, 500 g",
                        "price": 3.29,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00255763.jpg?a01479379fc1e22e801abb7abcf4a4b3",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.207Z",
                        "updated": "2025-01-14T22:05:48.207Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c8b",
                        "title": "Večzrnati toast, Ölz, 500 g",
                        "price": 3.29,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00255768.jpg?41047264a8a07b71ba0a79c2d56eb3f3",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.225Z",
                        "updated": "2025-01-14T22:05:48.225Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c8e",
                        "title": "Črni hlebec, Mercator, 1 kg",
                        "price": 2.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00238031.jpg?dd7dfee39f66dc1667657743df0c2887",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.237Z",
                        "updated": "2025-01-14T22:05:48.237Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c91",
                        "title": "Polbeli Jelenov kruh, Žito, 1 kg",
                        "price": 4.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00427633.jpg?6d142aeb7aa8eace8b509358e3737e46",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.249Z",
                        "updated": "2025-01-14T22:05:48.249Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c94",
                        "title": "Mešani temni kruh Skorjavc, Pekarna Grosuplje, 1 kg",
                        "price": 4.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00653193.jpg?499d5cdd33348afa35c37b141ff0574b",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.259Z",
                        "updated": "2025-01-14T22:05:48.259Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c97",
                        "title": "Mešani hlebec Hribovc, Žito, 1 kg",
                        "price": 4.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00293626.jpg?374d7fe995de4652170dff11f2dce9ad",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.272Z",
                        "updated": "2025-01-14T22:05:48.272Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c9a",
                        "title": "Hrustljavi pšenični kruhki s sezamom, Wasa, 200 g",
                        "price": 2.69,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00000095.jpg?6f88037204a67e83845dd245363bcd09",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.289Z",
                        "updated": "2025-01-14T22:05:48.289Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775c9d",
                        "title": "Mešani kruh Krjavelj, Pekarna Grosuplje, 1 kg",
                        "price": 4.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00220414.jpg?d5f46b511cc78295ceccc86792daf86b",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.301Z",
                        "updated": "2025-01-14T22:05:48.301Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775ca0",
                        "title": "Črni kruh Matevž, Pekarna Grosuplje, 1 kg",
                        "price": 4.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00505793.jpg?6ec720cc1c969c28e9fca915cbb53992",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.318Z",
                        "updated": "2025-01-14T22:05:48.318Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775ca3",
                        "title": "Sosedov kruh s semeni, Pekarna Grosuplje, 700 g",
                        "price": 4.99,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00220420.jpg?2c2e6d312cc1efea9a7367d7489e4cd9",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.327Z",
                        "updated": "2025-01-14T22:05:48.327Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775ca6",
                        "title": "Pecivo Sacher kocke, Mercator, pakirano, 350 g",
                        "price": 5.69,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00767217.jpg?b5fea975763357cfdd4fff3d127a1258",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.340Z",
                        "updated": "2025-01-14T22:05:48.340Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775ca9",
                        "title": "Mini princes krofi, Mercator, pakirano, 300 g",
                        "price": 4.85,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00769427.jpg?910ebb485f78edde320d48ab6f4fc56a",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.360Z",
                        "updated": "2025-01-14T22:05:48.360Z",
                        "__v": 0
                    },
                    {
                        "_id": "6786dfbc3eceb78881775cac",
                        "title": "Sosedova kajzerica, Pekarna Grosuplje, 65 g",
                        "price": 0.36,
                        "imageURL": "https://images.mercatoronline.si/SMALL/00577830.jpg?ccd4be1fe048407ff01a1fa76ff79bd4",
                        "discount": 0,
                        "category": "breadAndPastry",
                        "store": "mercator",
                        "added": "2025-01-14T22:05:48.370Z",
                        "updated": "2025-01-14T22:05:48.370Z",
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
