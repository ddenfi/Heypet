const express = require("express");
const router = express.Router();

const {
  getUsers,
  getUserByToken,
  updateBio,
  updatePhoto,
} = require("../../controllers/userController");
const { uploadSingle } = require("../../utils/files");

const auth = require("../../middlewares/auth");

router.route("/").get(auth("user"), getUsers);
router.route("/session").get(auth(), getUserByToken).put(auth(), updateBio);

router.put(
  "/session/photo",
  auth(),
  uploadSingle("photo", "photo", "./public/user"),
  updatePhoto
);

module.exports = router;
