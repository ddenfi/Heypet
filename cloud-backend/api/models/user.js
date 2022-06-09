"use strict";
const { Model } = require("sequelize");
const crypto = require("crypto");
const dayjs = require("dayjs");
const config = require("../config/config");

const bcrypt = require("bcryptjs");

module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {}
  }
  User.init(
    {
      name: DataTypes.STRING,
      bio: DataTypes.STRING,
      pet: DataTypes.STRING,
      photo: DataTypes.STRING,
      email: {
        type: DataTypes.STRING,
        unique: { msg: "Email address already in use!" },
        allowNull: false,
        validate: {
          isEmail: {
            args: true,
            msg: "Please enter an valid email address",
          },
        },
      },
      phoneNumber: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
        valdate: {
          isNumber: {
            args: true,
            msg: "Only digits are allowed",
          },
          len: {
            args: [10, 13],
            msg: "Please insert an valid phone number",
          },
        },
      },
      password: {
        type: DataTypes.STRING,
        allowNull: false,
        validate: {
          len: {
            args: [3, 64],
            msg: "Password must be greater than or equal to 3 characters",
          },
        },
      },
      role: {
        type: DataTypes.STRING,
        defaultValue: "user",
        validate: {
          isIn: {
            args: [["user", "admin"]],
            msg: "Invalid role name",
          },
        },
      },
    },
    {
      //managing databases such as connecting tables etc
      sequelize,
      modelName: "User",
      tableName: "users",
      indexes: [
        {
          unique: true,
          fields: ["email"],
        },
      ],
    }
  );

  //trigger database
  User.addHook("beforeCreate", async (user, options) => {
    if (user.password) {
      const salt = await bcrypt.genSalt(8);
      user.password = await bcrypt.hash(user.password, salt);

      const apiKey = crypto
        .createHash("sha224")
        .update(
          `${config.apiKeyPrefix}-${user.email}-${dayjs().format("YYYYMMDDHH")}`
        )
        .digest("hex");

      user.apiKey = apiKey;
    }
  });

  User.prototype.comparePassword = async (passwordBody, passwordDB) => {
    return await bcrypt.compare(passwordBody, passwordDB);
  };

  //User.sync();
  return User;
};
