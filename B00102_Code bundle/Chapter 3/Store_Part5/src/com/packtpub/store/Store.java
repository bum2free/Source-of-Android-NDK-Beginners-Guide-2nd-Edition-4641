package com.packtpub.store;

public class Store {
    static {
        System.loadLibrary("com_packtpub_store_Store");
    }

    public native int getCount();

    public native String getString(String pKey);
    public native void setString(String pKey, String pString);
}
