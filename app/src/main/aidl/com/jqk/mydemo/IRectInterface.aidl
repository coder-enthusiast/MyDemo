// IRectInterface.aidl
package com.jqk.mydemo;

// Declare any non-default types here with import statements

interface IRectInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String inRectInfo(in Rect rect);

    String outRectInfo(out Rect rect);

    String inOutRectInfo(inout Rect rect);
}
