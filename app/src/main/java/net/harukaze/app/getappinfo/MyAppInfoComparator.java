package net.harukaze.app.getappinfo;

import java.util.Comparator;

public class MyAppInfoComparator
        implements Comparator<MyAppInfo> {
    @Override
    public int compare(MyAppInfo i1, MyAppInfo i2) {
        return i1.getLabel().compareTo(i2.getLabel());
    }
}