const Sequelize = require('sequelize');

var name = "tbl_policy_type";
var definition = {
  id: {
    field: "id",
    primaryKey: true,
    type: Sequelize.BIGINT
  },
  item_name: {
    field: "item_name",
    type: Sequelize.STRING,
    validate: {
      max: 256
    }
  },
  description: {
    field: "description",
    type: Sequelize.STRING
  },
  definition: {
    field: "definition",
    type: Sequelize.STRING
  },
  internal_name: {
    field: "internal_name",
    type: Sequelize.STRING,
    validate: {
      max: 128
    }
  },
  conflict_resolution_mode: {
    field: "conflict_resolution_mode",
    type: Sequelize.ENUM('PRIORITY','CUSTOM')
  },
  project_id: {
    field: "project_id",
    type: Sequelize.STRING,
    validate: {
      max: 40
    }
  }
};

module.exports = {
  name: name,
  definition: definition,
  tableName: "tbl_policy_type"
};


