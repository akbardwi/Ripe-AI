const validation = require('../config/validation.config.js');
module.exports = app => {
    const authentication = require("../controllers/authentication.controller.js");
    var router = require("express").Router();

    // Register User
    router.post("/register", validation.signupValidation, validation.result, authentication.register);
    app.use('/api/authentication', router);
};
