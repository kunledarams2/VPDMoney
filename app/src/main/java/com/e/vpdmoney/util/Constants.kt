package com.e.vpdmoney.util





object Constants {
    object PrefKeys {
        const val ACCESS_TOKEN = "sessionId"
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
        const val USER_CREDENTIALS = "USER_CREDENTIALS"
        const val LOGGED_IN_USER = "LOGGED_IN_USER"
        const val RESET_PASSWORD_EMAIL = "RESET_PASSWORD_EMAIL"
        const val ACCOUNT_DETAILS = "ACCOUNT_DETAIL"
        const val TERMINAL_PARAMS_PROFILE = "TERMINAL PARAMS PROFILE"
        const val TERMINAL_PARAMS = "TERMINAL PARAMS"
        const val ORGANISATION_DETAILS = "ORGANISATION"
        const val ACCOUNT_DETAILS_TERMINAL = "ACCOUNT_DETAIL_TERMINAL"

    }


    object FormatPattern {
        const val MYSQL_DATE = "yyyy-MM-dd"
        const val DISPLAY_DATE = "dd MMMM yyyy"
        const val API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    object Database {
        const val DATABASE_NAME = "vpdmoney.db"
    }

    object Camera {
        const val RATIO_4_3_VALUE = 4.0 / 3.0
        const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    object APIDataKeys {
        const val SESSION_ID = "sessionId"
    }




    object APIResponseCodes {
        const val CODE_INVALID_SESSION_ERROR = 106
    }


}