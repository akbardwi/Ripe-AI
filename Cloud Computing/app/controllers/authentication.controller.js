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
                return res.status(500).send({
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

// Login Users
exports.login = (req, res, next) => {
    try {
        Users.findByEmail(req.body.email, (err, data) => {
            if (err) {
                if (err.kind === "not_found") {
                    return res.status(404).send({
                        success: false,
                        message: `Not found User with email ${req.body.email}.`
                    });
                } else {
                    return res.status(500).send({
                        success: false,
                        message: "Error retrieving User with email " + req.body.email
                    });
                }
            } else {
                password = bcrypt.compareSync(req.body.password, data.password);
                if(password) {
                    delete data.password;
                    if(data.status == "active") {
                        response = {success: true, message: "Login Successful", data: data};
                        res.send(response);
                    } else if(data.status == "pending") {
                        response = {success: false, message: "User is pending activation"};
                        res.send(response);
                    } else if(data.status == "blocked") {
                        response = {success: false, message: "User is blocked by Admin"};
                        res.send(response);
                    }
                } else {
                    response = {success: false, message: "Invalid Password"};
                    res.send(response);
                }
            };
        });
    } catch (err) {
        next(err);
    }
};
