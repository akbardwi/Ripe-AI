const sql = require("../models/db.js");
const Users = require("../models/users.model.js");
const { body, param, validationResult } = require("express-validator");

module.exports = {
    // User name and email Validation
    signupValidation: [
        body("firstname", "The firstname must be of minimum 3 characters length")
        .not()
        .isEmpty()
        .withMessage("firstname is required")
        .isLength({ min: 3 })
        .isAlpha()
        .withMessage("The firstname must be of alphabets only")
        .trim()
        .unescape()
        .escape(),

        body("lastname", "The lastname must be of minimum 3 characters length")
        .not()
        .isEmpty()
        .withMessage("firstname is required")
        .isLength({ min: 3 })
        .isAlpha()
        .withMessage("The lastname must be of alphabets only")
        .trim()
        .unescape()
        .escape(),

        body("phone", "The phone must be of minimum 10 characters length")
        .not()
        .isEmpty()
        .withMessage("phone is required")
        .isLength({ min: 10 })
        .isInt()
        .withMessage("The phone must be of numbers only")
        .trim()
        .unescape()
        .escape(),

        body("email", "Invalid email address")
        .not()
        .isEmpty()
        .withMessage("email is required")
        .trim()
        .unescape()
        .escape()
        .isEmail()
        .custom((value, {req}) => {
            return new Promise((resolve, reject) => {
                sql.query(`SELECT * FROM users WHERE email = ?`, value,(err,res)=> {
                if(err) {
                    reject(new Error('Server Error'))
                }
            
                if(res.length > 0) {
                    reject(new Error('E-mail already in use'))
                }
            
                resolve(true)
            
                });
        
            });
        }),
        

        body("password", "The password must be of minimum 6 characters length")
        .not()
        .isEmpty()
        .withMessage("password is required")
        .isLength({ min: 6 })
        .trim()
        .unescape()
        .escape(),
    ],

    // User ID Validation
    userID: [param("id", "Invalid User ID").trim().isInt()],

    // Checking Validation Result
    result: (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(422).json({ success: false, errors: errors.array() });
        }
        next();
    },
};
