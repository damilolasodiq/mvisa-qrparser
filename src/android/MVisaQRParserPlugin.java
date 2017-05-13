package com.ng.fidelitybank.mvisa.qrparser;

import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.visa.mvisa.tlvparser.*;

public class MVisaQRParserPlugin extends CordovaPlugin {

    private static final String TAG = "MVisaQRParserPlugin";

    private static final String PARSE_QR_DATA = "parseQrData";

    /**
     * Entry point for JavaScript calls.
     */
    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Executing action: " + action);
        if (action.equalsIgnoreCase(PARSE_QR_DATA)) {
            parseQrData(args, callbackContext);
        }
        return true;
    }

    private void parseQrData(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "parseQrData Method");
        try {
            String qrDataString = args.getString(0);
            QrCodeParser qrCodeParser = new QrCodeParser();
            String qrCodeParserResponseString = qrCodeParser.parseQrDataAsJson(qrDataString);

            if (qrCodeParserResponseString != null) {
                JSONObject qrCodeParserResponse = new JSONObject(qrCodeParserResponseString);
                JSONArray qrCodeParserError = qrCodeParserResponse.getJSONArray("qrCodeError");
                if (qrCodeParserError != null && qrCodeParserError.length() > 0) {
                    callbackContext.error(qrCodeParserError.toString());
                } else {
                    callbackContext.success(qrCodeParserResponse.getJsonObject("qrCodeData"));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while reading scanner qr code", e);
            callbackContext.error(e.getMessage());
        }
        return;
    }
}
