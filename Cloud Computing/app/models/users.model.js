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
            result(err, null);
            return;
        }

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
            result(err, null);
            return;
        }

        if (res.length) {
            result(null, res);
            return;
        }

        // not found Tutorial with the id
        result({
            kind: "not_found"
        }, null);
    });
};

Users.checkLogin = (email, result) => {
    sql.query(`SELECT * FROM users WHERE email = '${email}'`, (err, res) => {
      if (err) {
        result(err, null);
        return;
      }
  
      if (res.length) {
        result(null, res[0]);
        return;
      }
  
      // not found Tutorial with the id
      result({ kind: "not_found" }, null);
    });
  };

module.exports = Users;
