package com.getsentry.raven.android;

import com.getsentry.raven.stub.SentryStub;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.Callable;

@RunWith(RobolectricTestRunner.class)
public class RavenIT extends AndroidTest {
    
    @Test
    public void test() throws Exception {
        Assert.assertEquals(sentryStub.getEventCount(), 0);

        RavenITActivity activity = Robolectric.setupActivity(RavenITActivity.class);
        activity.sendEvent();

        waitUntilTrue(1000, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return sentryStub.getEventCount() == 1;
            }
        });

        Assert.assertEquals(sentryStub.getEventCount(), 1);
    }

}
