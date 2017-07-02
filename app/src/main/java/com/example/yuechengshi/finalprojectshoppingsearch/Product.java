package com.example.yuechengshi.finalprojectshoppingsearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuechengshi on 7/1/17.
 */

public class Product implements Parcelable {
    public String brandName;
    public String thumbnailImageUrl;
    public int productId;
    public String originPrice;
    public int styleId;
    public int colorId;
    public String price;
    public String percentOff;
    public String productUrl;
    public String productName;

    public Product(String brandName, String thumbnailImageUrl, int productId, String originPrice, int styleId, int colorId, String price, String percentOff, String productUrl, String productName) {
        this.brandName = brandName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productId = productId;
        this.originPrice = originPrice;
        this.styleId = styleId;
        this.colorId = colorId;
        this.price = "US " + price;
        this.percentOff = percentOff + " off";
        this.productUrl = productUrl;
        this.productName = productName;

    }



    public String toString() {
        return productName + ", " + price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brandName);
        dest.writeString(thumbnailImageUrl);
        dest.writeInt(productId);
        dest.writeString(originPrice);
        dest.writeInt(styleId);
        dest.writeInt(colorId);
        dest.writeString(price);
        dest.writeString(percentOff);
        dest.writeString(productUrl);
        dest.writeString(productName);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        brandName = in.readString();
        thumbnailImageUrl = in.readString();
        productId = in.readInt();
        originPrice = in.readString();
        styleId = in.readInt();
        colorId = in.readInt();
        price = in.readString();
        percentOff = in.readString();
        productUrl = in.readString();
        productName = in.readString();
    }

}

