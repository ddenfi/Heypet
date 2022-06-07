const catchAsync = require("../utils/catchAsync");
const { Post, Category } = require("../models");
const { pagination } = require("../utils/helper");
const { Sequelize } = require("sequelize");
const sequelize = Sequelize;
const { getFirestore } = require("firebase-admin/firestore");

exports.getPosts = catchAsync(async (req, res, next) => {
  const rows = await Post.findAll({
    ...pagination(req),
    order: sequelize.literal("createdAt	DESC"),
  });

  res.status(200).json({
    status: "success",
    results: rows.length,
    data: {
      categories: rows,
    },
  });
});

exports.getPostsByCategoryId = catchAsync(async (req, res, next) => {
  const rows = await Post.findAll({
    ...pagination(req),
    where: {
      categoryId: req.params.categoryId,
    },
    include: Category,
    order: sequelize.literal("createdAt	DESC"),
  });

  res.status(200).json({
    status: "success",
    results: rows.length,
    data: {
      categories: rows,
    },
  });
});

exports.createPost = catchAsync(async (req, res, next) => {
  const db = getFirestore();
  var today = new Date();

  const doc = await db.collection("feeds").add({
    date: today,
  });

  if (req.file) req.body["photo"] = req.file.filename;
  const rows = await Post.create({
    categoryId: req.body.categoryId,
    description: req.body.description,
    photo: `${process.env.BASE_URL}feeds/${req.body.photo}`,
    idFeeds: doc.id,
    userName: req.user.name,
  });

  res.status(200).json({
    status: "success",
    data: {
      categories: rows,
    },
  });
});

exports.deletePost = catchAsync(async (req, res, next) => {
  const rows = await Post.destroy({
    where: {
      id: req.params.id,
    },
  });

  res.status(200).json({
    status: "success",
  });
});

exports.getPostById = catchAsync(async (req, res, next) => {
  const rows = await Post.findOne({
    where: {
      id: req.params.id,
    },
  });

  res.status(200).json({
    status: "success",
    data: {
      categories: rows,
    },
  });
});
