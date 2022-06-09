const catchAsync = require("../utils/catchAsync");
const { Category } = require("../models");
const { Sequelize } = require("sequelize");
const sequelize = Sequelize;

exports.getCategories = catchAsync(async (req, res, next) => {
  const rows = await Category.findAll({
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

exports.createCategory = catchAsync(async (req, res, next) => {
  const rows = await Category.create({
    name: req.body.name,
  });

  res.status(200).json({
    status: "success",
    data: {
      categories: rows,
    },
  });
});

exports.deleteCategory = catchAsync(async (req, res, next) => {
  const rows = await Category.destroy({
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

exports.updateCategory = catchAsync(async (req, res, next) => {
  const rows = await Category.update(
    { name: req.body.name },
    {
      where: {
        id: req.params.id,
      },
    }
  );

  res.status(200).json({
    status: "success",
    data: {
      categories: rows,
    },
  });
});

exports.getCategoryById = catchAsync(async (req, res, next) => {
  const rows = await Category.findByPk(req.params.id);

  res.status(200).json({
    status: "success",
    data: {
      categories: rows,
    },
  });
});
