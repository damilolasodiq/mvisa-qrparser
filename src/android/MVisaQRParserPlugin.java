package com.ng.fidelitybank.mvisa.qrparser;

import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.visa.mvisa.tlvparser.*;

public class MVisaQRParserPlugin extends CordovaPlugin {

    private static final String TAG = "MVisaQRParserPlugin";

    private static final String PARSE_QR_DATA="parseQrData";

    /**
     * Entry point for JavaScript calls.
     */
    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Executing action: " + action);
        switch (action) {
            case PARSE_QR_DATA:
              parseQrData(callbackContext);
              break;
            default:
                return false;
        }
        return true;
    }

    private void parseQrData(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "parseQrData Method");
        try {
            String qrDataString = args.getString(0);
            QrCodeParser qrCodeParser = new QrCodeParser();
            QrCodeParserResponse qrCodeParserResponse = qrCodeParser.parseQrData(qrDataString);
            if(qrCodeParserResponse!=null){
              if(qrCodeParserResponse.getQrCodeError()!=null && !qrCodeParserResponse.getQrCodeError().isEmpty()){
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, qrCodeParserResponse.getQrCodeError()));
              }else{
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, qrCodeParserResponse.getQrCodeData()));
              }
            }
        } catch (Exception e) {
            Log.e(TAG,"Error while reading scanner qr code",e);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, e.getMessage()));
        }
        return;
    }
}
