const sql = require("./db.js");

// constructor
const History = function (history) {
    this.email_user = history.email_user;
    this.fruit = history.fruit;
    this.ripeness = history.ripeness;
    this.img_url = history.img_url;
};

History.create = (newHistory, result) => {
    sql.query("INSERT INTO history SET ?", newHistory, (err, res) => {
        if (err) {
            result(err, null);
            return;
        }

        result(null, {
            id: res.insertId,
            ...newHistory
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
            result(null, err);
            return;
        }

        result(null, res);
    });
};

module.exports = History;
