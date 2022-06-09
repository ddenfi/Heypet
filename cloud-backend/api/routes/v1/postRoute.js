const express = require("express");
const router = express.Router();
const auth = require("../../middlewares/auth");

const {
  createPost,
  getPosts,
  getPostById,
  deletePost,
  getPostsByCategoryId,
  getPostByIdUser,
  getPostByToken,
} = require("../../controllers/postController");

const { uploadSingle } = require("../../utils/files");

router.post(
  "/feed",
  auth(),
  uploadSingle("photo", "photo", "./public/feeds"),
  createPost
);

router.route("/").get(getPosts).get(auth(), getPostByToken);

router.route("/category/:categoryId").get(getPostsByCategoryId);
router.route("/profile/feeds/:idUser").get(getPostByIdUser);

router.route("/:id").delete(deletePost).get(getPostById);

module.exports = router;
