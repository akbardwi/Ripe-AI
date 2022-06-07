const sql = require("./db.js");

// constructor
const History = function (history) {
    this.email_user = history.email_user;
    this.fruit = history.fruit;
    this.ripeness = history.ripeness;
};

History.create = (newUser, result) => {
    sql.query("INSERT INTO history SET ?", newUser, (err, res) => {
        if (err) {
            console.log("error: ", err);
            result(err, null);
            return;
        }

        console.log("created history: ", {
            id: res.insertId,
            ...newUser
        });
        result(null, {
            id: res.insertId,
            ...newUser
        });
    });
};

History.getAll = (email, result) => {
    let query = "SELECT * FROM history";

    if (email) {
        query += ` WHERE email_user = '${email}'`;
    }

    sql.query(query, (err, res) => {
        if (err) {
            console.log("error: ", err);
            result(null, err);
            return;
        }

        console.log("history: ", res);
        result(null, res);
    });
};

module.exports = History;
