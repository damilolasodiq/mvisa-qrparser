var exec = require('cordova/exec');

var MVisaQRParser = {
  parseQrData: function (qrDataString,onSuccess,onFailure){
    if (typeof qrDataString === "undefined" || qrDataString === null) qrDataString = "";
    exec(onSuccess, onFailure, "MVisaQRParserPlugin", "parseQrData", [qrDataString]);
  }
}
module.exports = MVisaQRParser;
