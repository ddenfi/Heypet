"use strict";
const { Model } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
  class Animal extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {}
  }
  Animal.init(
    {
      name: DataTypes.STRING,
      animalType: DataTypes.STRING,
      personality: DataTypes.TEXT,
      health: DataTypes.TEXT,
      grooming: DataTypes.TEXT,
    },
    {
      //managing databases such as connecting tables etc
      sequelize,
      modelName: "Animal",
      tableName: "animals",
      indexes: [
        {
          unique: true,
          fields: ["name"],
        },
      ],
    }
  );

  //Animal.sync();
  return Animal;
};
