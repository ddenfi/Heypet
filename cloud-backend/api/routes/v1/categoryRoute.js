const express = require("express");
const router = express.Router();

const categoryController = require("../../controllers/categoryController");

const auth = require("../../middlewares/auth");

router
  .route("/")
  .get(categoryController.getCategories)
  .post(categoryController.createCategory);

router
  .route("/:id")
  .delete(categoryController.deleteCategory)
  .put(categoryController.updateCategory)
  .get(categoryController.getCategoryById);

module.exports = router;
