const validation = require('../config/validation.config.js');
module.exports = app => {
    const users = require("../controllers/users.controller.js");
    var router = require("express").Router();

    // Get User
    router.get("/user", users.findAll);
    
    app.use('/api/', router);
};
