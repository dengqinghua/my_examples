'use strict';

const config = {};

config.a1 = () => {
  return 1;
};

console.log(module.exports === exports);
exports = module.exports = config;
console.log(module.exports === exports);
