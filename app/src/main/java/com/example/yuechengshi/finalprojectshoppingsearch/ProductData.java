package com.example.yuechengshi.finalprojectshoppingsearch;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yuechengshi on 7/1/17.
 */

public class ProductData extends AsyncTask<String, Void, ArrayList<Product>> {

    private final Context mContext;

    //store the product data
    ArrayList<Product> mProduct;

    private final String LOG = ProductData.class.getSimpleName();

    public ProductData(Context context, ArrayList<Product> product){
        this.mContext = context;
        this.mProduct = product;
    }

    @Override
    protected ArrayList<Product> doInBackground(String... params) {
        if(params.length == 0){
            return null;
        }

        String productQuery = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String productJsonStr = null;

        try{


            String baseUrl = "https://api.zappos.com/Search?";
            String api = "b743e26728e16b81da139182bb2094357c31d331";

            String TERM_PARAM = "term";
            String API_PARAM = "key";


            Uri uri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(TERM_PARAM, params[0])
                    .appendQueryParameter(API_PARAM, api)
                    .build();

            URL zapposUrl = new URL(uri.toString());

            urlConnection = (HttpURLConnection) zapposUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inputStream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }


            if(buffer.length() == 0){
                return null;
            }

            productJsonStr = buffer.toString();


            Log.v(LOG, productJsonStr);


        }catch (IOException e){
            return null;
        } finally {

            if(urlConnection != null){
                urlConnection.disconnect();
            }

            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    Log.e(LOG, "closing stream error", e);
                }
            }
        }

        try{
            return getProductDataFromJson(productJsonStr);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> result) {
        if (result != null) {
            mProduct.clear();
            for(Product i : result) {
                mProduct.add(i);
                Main2Activity.adapter.notifyDataSetChanged();
                Log.v(LOG,mProduct.toString());
            }

        }
    }

    //pull out the data from the JSON Format
    private ArrayList<Product> getProductDataFromJson(String productJsonStr) throws JSONException {
        //The name of data we want to pull out from the Json
        String RESULT = "results";
        String BRAND_NAME = "brandName";
        String IMAGE = "thumbnailImageUrl";
        String PRODUCT_ID = "productId";
        String ORIGINAL_PRICE = "originalPrice";
        String STYLE_ID = "styleId";
        String COLOR_ID = "colorId";
        String PRICE = "price";
        String PERCENT_OFF = "percentOff";
        String PRODUCT_URL = "productUrl";
        String PRODUCT_NAME = "productName";


        ArrayList<Product> result = new ArrayList<>();
        //get JSON array in the results
        JSONObject productJSON = new JSONObject(productJsonStr);
        JSONArray productArray = productJSON.getJSONArray(RESULT);

        String[] resultStrTest = new String[productArray.length()];
        for(int i = 0; i < productArray.length(); i++){
            String mBrandName;
            String mThumbnailImageUrl;
            int mProductId;
            String mOriginPrice;
            int mStyleId;
            int mColorId;
            String mPrice;
            String mPercentOff;
            String mProductUrl;
            String mProductName;

            JSONObject product = productArray.getJSONObject(i);

            //get all the data;
            mBrandName = product.getString(BRAND_NAME);
            mThumbnailImageUrl = product.getString(IMAGE);
            mProductId = product.getInt(PRODUCT_ID);
            mOriginPrice = product.getString(ORIGINAL_PRICE);
            mStyleId = product.getInt(STYLE_ID);
            mColorId = product.getInt(COLOR_ID);
            mPrice = product.getString(PRICE);
            mPercentOff = product.getString(PERCENT_OFF);
            mProductUrl = product.getString(PRODUCT_URL);
            mProductName = product.getString(PRODUCT_NAME);


            result.add(new Product(mBrandName,mThumbnailImageUrl,mProductId,mOriginPrice,mStyleId,mColorId,mPrice,mPercentOff,mProductUrl,mProductName));
        }

        return result;


    }
}
