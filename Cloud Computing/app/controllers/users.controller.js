const Users = require("../models/users.model.js");

exports.findAll = (req, res) => {
    const email = req.query.email;

    Users.findByEmail(email, (err, data) => {
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
