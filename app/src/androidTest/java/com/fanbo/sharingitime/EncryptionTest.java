package com.fanbo.sharingitime;

import android.util.Log;

import com.fanbo.sharingitime.util.EncryptionUtil;

import junit.framework.TestCase;

/**
 * Created by fanbo on 2017/6/16.
 */

public class EncryptionTest extends TestCase{
    public void testMD5(){
        String aa = EncryptionUtil.getStrFromStr("dfsdfadfasdfasdf");
        Log.i("tag",aa);

    }
}
