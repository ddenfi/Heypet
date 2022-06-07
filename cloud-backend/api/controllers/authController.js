const catchAsync = require("../utils/catchAsync");
const {
  registerNewUser,
  loginWithEmailAndPassword,
} = require("../services/authService");
const { generateAuthToken } = require("../services/tokenService");

exports.register = catchAsync(async (req, res, next) => {
  if (req.file) req.body["photo"] = req.file.filename;

  const newUser = await registerNewUser(req.body);
  const token = await generateAuthToken(newUser);

  res.status(200).json({
    status: "success",
    data: {
      token,
      user: newUser,
    },
  });
});

exports.login = catchAsync(async (req, res, next) => {
  const user = await loginWithEmailAndPassword(req.body);
  const token = await generateAuthToken(user);

  res.status(200).json({
    status: "success",
    data: {
      token,
      user,
    },
  });
});
