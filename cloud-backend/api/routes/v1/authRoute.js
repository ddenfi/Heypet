const express = require("express");
const router = express.Router();

const { register, login } = require("../../controllers/authController");
const { uploadSingle } = require("../../utils/files");

router.post(
  "/register",
  uploadSingle("photo", "photo", "./public/user"),
  register
);
router.post("/login", login);

module.exports = router;
