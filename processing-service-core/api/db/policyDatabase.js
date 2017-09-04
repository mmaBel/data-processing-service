const Sequelize = require('sequelize');
var Q = require('q');
var policyTypeModel = require('./policyTypeModel.js');

module.exports = {
  getPolicyType: getPolicyType,
  getPolicyTypes: getPolicyTypes
};

const policyDatabase = new Sequelize('postgres://root:root@192.168.56.10:5432/workflow');
policyDatabase
  .authenticate()
  .then(() => {
    console.log('Connection has been established successfully.');
  })
  .catch(err => {
    console.error('Unable to connect to the database:', err);
  });

var policyType = policyDatabase.define(policyTypeModel.name, policyTypeModel.definition, {tableName:  policyTypeModel.tableName, timestamps: false });


function getPolicyType(projectId, policyTypeId){
  var getTypePromise = Q.defer();
  policyType.findById(policyTypeId).then(type => {
     getTypePromise.resolve(convertDbPolicyTypeResultToExpectedForm(type));
  });
  return getTypePromise.promise;  
}

function getPolicyTypes(projectId, pageStart, pageSize){
  var getTypePromises = Q.defer();
  policyType.findAndCountAll({ 
      offset: pageStart, 
      limit: pageSize
    }).then(types => {
    getTypePromises.resolve(convertDbPolicyTypesResultToExpectedForm(types));
  });
  return getTypePromises.promise;  
}

function convertDbPolicyTypesResultToExpectedForm(dbResults){
  var finalResult = {
    results: [],
    totalhits: dbResults.count
  };
  for(var dbResult of dbResults.rows) {
    convertDbPolicyTypeResultToExpectedForm(dbResult);
    finalResult.results.push(dbResult)
  }
  return finalResult;
}

function convertDbPolicyTypeResultToExpectedForm(dbResult){
  dbResult.additional = {
    short_name: dbResult.internal_name,
    definition: JSON.parse(dbResult.definition)
  };
  return dbResult;
}