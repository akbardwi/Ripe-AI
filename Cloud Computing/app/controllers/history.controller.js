const History = require("../models/history.model.js");

// Create and Save a new History
exports.saveHistory = (req, res, next) => { 
    // Create a History
    var imageUrl = ''
    if (req.file && req.file.cloudStoragePublicUrl) {
        imageUrl = req.file.cloudStoragePublicUrl
    }

    const history = new History({email_user: req.body.email, fruit: req.body.fruit, ripeness: req.body.ripeness, img_url: imageUrl});

    // Save History in the database
    try {
        History.create(history, (err, data) => {
            if (err) 
                return res.status(500).send({
                    message: err.message || "Some error occurred while creating the History."
                });
             else 
                console.log(data);
            
            response = {
                success: true,
                data: data
            };
            res.send(response);
        });
    } catch (err) {
        next(err);
    }
};

exports.findAll = (req, res) => {
    const email = req.query.email;

    History.getAll(email, (err, data) => {
        if (err) 
            return res.status(500).send({
                message: err.message || "Some error occurred while retrieving history."
            });
         else 
            response = {
                success: true,
                data: data
            };
            res.send(response);        
    });
};
