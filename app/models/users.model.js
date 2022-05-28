const sql = require("./db.js");

// constructor
const Users = function(users) {
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
    
    console.log("created tutorial: ", { id: res.insertId, ...newUser });
    result(null, { id: res.insertId, ...newUser });
  });
};

module.exports = Users;
