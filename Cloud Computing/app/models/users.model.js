const sql = require("./db.js");

// constructor
const Users = function (users) {
    this.firstname = users.firstname;
    this.lastname = users.lastname;
    this.phone = users.phone;
    this.email = users.email;
    this.password = users.password;
};

Users.create = (newUser, result) => {
    sql.query("INSERT INTO users SET ?", newUser, (err, res) => {
        if (err) {
            console.log("error: ", err);
            result(err, null);
            return;
        }

        console.log("created user: ", {
            id: res.insertId,
            ...newUser
        });
        result(null, {
            id: res.insertId,
            ...newUser
        });
    });
};

Users.findByEmail = (email, result) => {
    let query = "SELECT * FROM users";

    if (email) {
        query += ` WHERE email = '${email}'`;
    }
    sql.query(query, (err, res) => {
        if (err) {
            console.log("error: ", err);
            result(err, null);
            return;
        }

        if (res.length) {
            console.log("found user: ", res[0]);
            result(null, res);
            return;
        }

        // not found Tutorial with the id
        result({
            kind: "not_found"
        }, null);
    });
};

module.exports = Users;
