const httpStatus = require("http-status");
const AppError = require("../utils/appError");
const { User, Token } = require("../models/index");
const tokenService = require("./tokenService");
const { tokenTypes } = require("../config/tokens");

/**
 *
 * @param {Object} body
 * @returns New User
 */
const registerNewUser = async (body) => {
  var { name, bio, email, phoneNumber, photo, password } = body;

  photo = `${process.env.BASE_URL}user/${photo}`;

  const user = await User.create({
    name,
    bio,
    email,
    phoneNumber,
    password,
    photo,
  });

  user.password = undefined;
  return user;
};

/**
 *
 * @param {Object} body
 * @returns User
 */
const loginWithEmailAndPassword = async (body) => {
  // 1) check if email and password are not filled
  if (!body.email || !body.password) {
    throw new AppError(
      "Please provide an email & password",
      httpStatus.BAD_REQUEST
    );
  }

  // 2) get user from database
  const user = await User.findOne({
    where: {
      email: body.email,
    },
    attributes: {
      exclude: ["createdAt", "updatedAt"],
    },
  });

  // 3) check if user not found

  console.log(body.password);
  console.log(user.comparePassword(body.password, user.password));
  if (!user) {
    throw new AppError("Invalid email", httpStatus.UNAUTHORIZED);
  } else if (!(await user.comparePassword(body.password, user.password))) {
    throw new AppError("Invalid password", httpStatus.UNAUTHORIZED);
  } else if (
    !user &&
    !(await user.comparePassword(body.password, user.password))
  ) {
    throw new AppError("Invalid email & password", httpStatus.UNAUTHORIZED);
  }

  user.password = undefined;

  return user;
};

/**
 * Reset password
 * @param {string} resetPasswordToken
 * @param {string} newPassword
 * @returns {Promise}
 */
const resetPassword = async (resetPasswordToken, newPassword) => {
  try {
    const resetPasswordTokenDoc = await tokenService.verifyToken(
      resetPasswordToken,
      tokenTypes.RESET_PASSWORD
    );
    const user = await User.findByPk(resetPasswordTokenDoc.userId);
    if (!user) {
      throw new AppError(
        "Reset password failed, user not found",
        httpStatus.NOT_FOUND
      );
    }
    user.password = newPassword;
    await user.save();

    await Token.destroy({
      where: {
        userId: user.id,
        type: tokenTypes.RESET_PASSWORD,
      },
    });
  } catch (error) {
    throw new ApiError(httpStatus.UNAUTHORIZED, "Password reset failed");
  }
};

module.exports = {
  registerNewUser,
  loginWithEmailAndPassword,
  resetPassword,
};
