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
}
