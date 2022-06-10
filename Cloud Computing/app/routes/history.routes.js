const validation = require('../config/validation.config.js');
const imgUpload = require('../modules/imgUpload');
const Multer = require('multer')

const multer = Multer({
    storage: Multer.MemoryStorage,
    fileSize: 5 * 1024 * 1024
})

module.exports = app => {
    const history = require("../controllers/history.controller.js");
    var router = require("express").Router();

    // Save History
    router.post("/history", multer.single('attachment'), imgUpload.uploadToGcs, validation.saveHistoryValidation, validation.result, history.saveHistory);

    // Get History
    router.get("/history", history.findAll);
    
    app.use('/api/', router);
};
