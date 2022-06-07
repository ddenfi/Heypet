let data = [];

data.push({
  name: "Story",
});
data.push({
  name: "breeding",
});
data.push({
  name: "Adopsi",
});
data.push({
  name: "Tips",
});

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.bulkInsert("categories", data, {});
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.bulkDelete("categories", null, {});
  },
};
