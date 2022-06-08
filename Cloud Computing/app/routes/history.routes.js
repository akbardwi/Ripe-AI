const validation = require('../config/validation.config.js');
module.exports = app => {
    const history = require("../controllers/history.controller.js");
    var router = require("express").Router();

    // Save History
    router.post("/history", validation.saveHistoryValidation, validation.result, history.saveHistory);

    // Get History
    router.get("/history", history.findAll);
    
    app.use('/api/', router);
};
