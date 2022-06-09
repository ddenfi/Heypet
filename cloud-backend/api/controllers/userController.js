const catchAsync = require("../utils/catchAsync");
const { User } = require("../models/index");

exports.getUsers = catchAsync(async (req, res, next) => {
  const { count, rows } = await User.findAndCountAll({});

  res.status(200).json({
    status: "success",
    count,
    results: rows.length,
    data: {
      users: rows,
    },
  });
});

exports.getUserByToken = catchAsync(async (req, res, next) => {
  const rows = await User.findOne({
    where: {
      id: req.user.id,
    },
  });

  res.status(200).json({
    status: "success",
    results: rows.length,
    data: {
      users: rows,
    },
  });
});

exports.updateProfile = catchAsync(async (req, res, next) => {
  if (req.file) {
    req.body["photo"] = req.file.filename;
    const rows = await User.update(
      {
        photo: `${process.env.BASE_URL}user/${req.file.filename}`,
      },
      {
        where: {
          id: req.user.id,
        },
      }
    );
  }
  const rows = await User.update(
    {
      bio: req.body.bio,
      pet: req.body.pet,
    },
    {
      where: {
        id: req.user.id,
      },
    }
  );

  res.status(200).json({
    status: "success",
  });
});
