const exec = require('cordova/exec');

let AWSIoTPlugin = {
    connect: function (options, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'AWSIoTPlugin', 'connect', [options]);
    },
    publish: function (message, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'AWSIoTPlugin', 'publish', [message]);
    },
    subscribe: function (topic, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'AWSIoTPlugin', 'subscribe', [topic]);
    },
};

module.exports = AWSIoTPlugin;