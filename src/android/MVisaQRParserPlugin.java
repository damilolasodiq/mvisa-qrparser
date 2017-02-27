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
        if(action.equalsIgnoreCase(PARSE_QR_DATA)){
          parseQrData(args,callbackContext);
        }
        return true;
    }

    private void parseQrData(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "parseQrData Method");
        try {
            String qrDataString = args.getString(0);
            QrCodeParser qrCodeParser = new QrCodeParser();
            QrCodeParserResponse qrCodeParserResponse = qrCodeParser.parseQrCode(qrDataString);

            if(qrCodeParserResponse!=null){
              if(qrCodeParserResponse.getErrorCodesList()!=null && !qrCodeParserResponse.getErrorCodesList().isEmpty()){
                JSONArray jsonArray = new JSONArray();
                for (Integer errorCode:qrCodeParserResponse.getErrorCodesList()) {
                  jsonArray.put(errorCode);
                }
                callbackContext.error(jsonArray.toString());
              }else{
                JSONObject reponseObject = new JSONObject();
                QrCodeData qrCodeData= qrCodeParserResponse.getQrCodeData();
                reponseObject.put("payloadFormatIndicator", qrCodeData.getPayloadFormatIndicator());
                reponseObject.put("pointOfInitiation", qrCodeData.getPointOfInitiation());
                reponseObject.put("merchantId", qrCodeData.getMerchantId());
                reponseObject.put("masterCardPan1", qrCodeData.getMasterCardPan1());
                reponseObject.put("masterCardPan2", qrCodeData.getMasterCardPan2());
                reponseObject.put("npciid1", qrCodeData.getNpciid1());
                reponseObject.put("npciid2", qrCodeData.getNpciid2());
                reponseObject.put("mcc", qrCodeData.getMcc());
                reponseObject.put("currencyCode", qrCodeData.getCurrencyCode());
                reponseObject.put("transactionAmount", qrCodeData.getTransactionAmount());
                reponseObject.put("tipIndicatorValue", qrCodeData.getTipIndicatorValue());
                reponseObject.put("tipAmount", qrCodeData.getTipAmount());
                reponseObject.put("tipPercentage", qrCodeData.getTipPercentage());
                reponseObject.put("countryCode", qrCodeData.getCountryCode());
                reponseObject.put("merchantName", qrCodeData.getMerchantName());
                reponseObject.put("cityName", qrCodeData.getCityName());
                reponseObject.put("postalCode", qrCodeData.getPostalCode());
                reponseObject.put("billId", qrCodeData.getBillId());
                reponseObject.put("mobileNumber", qrCodeData.getMobileNumber());
                reponseObject.put("storeId", qrCodeData.getStoreId());
                reponseObject.put("loyaltyNumber", qrCodeData.getLoyaltyNumber());
                reponseObject.put("referenceId", qrCodeData.getReferenceId());
                reponseObject.put("consumerId", qrCodeData.getConsumerId());
                reponseObject.put("terminalId", qrCodeData.getTerminalId());
                reponseObject.put("purpose", qrCodeData.getPurpose());
                reponseObject.put("additionalConsumerDataRequest", qrCodeData.getAdditionalConsumerDataRequest());
                reponseObject.put("addDataMasterCard1", qrCodeData.getAddDataMasterCard1());
                reponseObject.put("addDataMasterCard2", qrCodeData.getAddDataMasterCard2());
                reponseObject.put("addDataNpci1", qrCodeData.getAddDataNpci1());
                reponseObject.put("addDataNpci2", qrCodeData.getAddDataNpci2());
                reponseObject.put("crc", qrCodeData.getCrc());
                reponseObject.put("isBillIdMandatory", qrCodeData.isBillIdMandatory());
                reponseObject.put("isMobileNoMandatory", qrCodeData.isMobileNoMandatory());
                reponseObject.put("isStoreIdMandatory", qrCodeData.isStoreIdMandatory());
                reponseObject.put("isLoyaltyNoMandatory", qrCodeData.isLoyaltyNoMandatory());
                reponseObject.put("isReferenceIdMandatory", qrCodeData.isReferenceIdMandatory());
                reponseObject.put("isConsumerIdMandatory", qrCodeData.isConsumerIdMandatory());
                reponseObject.put("isTerminalIdMandatory", qrCodeData.isTerminalIdMandatory());
                reponseObject.put("isPurposeMandatory", qrCodeData.isPurposeMandatory());
                reponseObject.put("isAdditonalConsumerDataRequestMandatory", qrCodeData.isAdditonalConsumerDataRequestMandatory());
                reponseObject.put("isAddDataMasterCard1Mandatory", qrCodeData.isAddDataMasterCard1Mandatory());
                reponseObject.put("isAddDataMasterCard2Mandatory", qrCodeData.isAddDataMasterCard2Mandatory());
                reponseObject.put("isAddDataNpci1Mandatory", qrCodeData.isAddDataNpci1Mandatory());
                reponseObject.put("isAddDataNpci2Mandatory", qrCodeData.isAddDataNpci2Mandatory());
                callbackContext.success(reponseObject);
              }
            }
        } catch (Exception e) {
            Log.e(TAG,"Error while reading scanner qr code",e);
            callbackContext.error(e.getMessage());
        }
        return;
    }
}
