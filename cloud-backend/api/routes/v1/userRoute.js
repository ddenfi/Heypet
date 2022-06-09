const express = require("express");
const router = express.Router();

const {
  getUsers,
  getUserByToken,
  updateProfile,
} = require("../../controllers/userController");
const { uploadSingle } = require("../../utils/files");

const auth = require("../../middlewares/auth");

router.route("/").get(auth(), getUsers);
router.route("/session").get(auth(), getUserByToken);

router.put(
  "/session/profile",
  auth(),
  uploadSingle("photo", "photo", "./public/user"),
  updateProfile
);

module.exports = router;
