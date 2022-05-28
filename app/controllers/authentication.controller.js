const Users = require("../models/users.model.js");
var bcrypt = require('bcryptjs');

// Create and Save a new Users
exports.register = (req, res, next) => {
    // Create a Users
    const users = new Users({
        firstname: req.body.firstname,
        lastname: req.body.lastname,
        phone: req.body.phone,
        email: req.body.email,
        password: req.body.password
    });

    // Save Users in the database
    try {
        users.password = bcrypt.hashSync(users.password, 10);
        Users.create(users, (err, data) => {
            if (err) 
                res.status(500).send({
                    message: err.message || "Some error occurred while creating the Users."
                });
            else 
                delete data.password;
                console.log(data);
                response = {success: true, data: data};
                res.send(response);        
        });
    } catch (err) {
        next(err);
    }
};
