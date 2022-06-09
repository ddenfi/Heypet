"use strict";
const { Model } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
  class Post extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      Post.belongsTo(models.Category);
    }
  }
  Post.init(
    {
      categoryId: DataTypes.INTEGER,
      description: DataTypes.TEXT,
      photo: DataTypes.STRING,
      idFeeds: DataTypes.STRING,
      userName: DataTypes.STRING,
      idUser: DataTypes.STRING,
      userPhoto: DataTypes.STRING,
    },
    {
      //managing databases such as connecting tables etc
      sequelize,
      modelName: "Post",
      tableName: "posts",
    }
  );

  //Post.sync();
  return Post;
};
