package com.hcmute.myanime.common;

import java.util.Random;

public class GlobalVariable {

    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_LIMIT = "9";
    public static String GetOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public static final String EMAIL_CONFIRMATION_STATUS_PENDING = "pending";
    public static final String EMAIL_CONFIRMATION_STATUS_USED = "Used";

    // The rule is that each view of a client can only be increased at least once every 30 minutes
    public static final long MINIMUM_SECONDS_VIEW = 1800;
}
