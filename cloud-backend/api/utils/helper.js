exports.pagination = (req) => {
  const limitQ = req.query.limit ? { limit: Number(req.query.limit) } : false;
  const page = req.query.page ? { page: Number(req.query.page) > 0 ? Number(req.query.page) : 1 } : false;
  const offsetQ = page ? { offset: (Number(req.query.page) - 1) * Number(req.query.limit) } : false;
  const orderQ = req.query.order ? req.query.order.split(':') : false;

  const order = { order: [orderQ ? [orderQ[0], orderQ[1]] : ['id', 'DESC']] };

  return { ...limitQ, ...offsetQ, ...order };
};

exports.jsonParse = (data) => {
  return JSON.parse(JSON.stringify(data));
};

exports.roundToNearest = (number, multipleOf) => {
  return Math.ceil(number / multipleOf) * multipleOf;
};
