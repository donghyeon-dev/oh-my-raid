package com.ohmyraid.utils;

import com.ohmyraid.common.suport.StaticContextAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

public class MessageUtils {
    static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    private static MessageSource messageSource = StaticContextAccessor.getBean(ReloadableResourceBundleMessageSource.class);

    public static String getMessage(String msg) {
        return getMessage(msg, new String[] {});
    }

    public static String getMessage(String msg, Object msgArgs) {
        return getMessage(msg, new Object[] {msgArgs}, null);
    }

    public static String getMessage(String msg, Object[] msgArgs) {
        return getMessage(msg, msgArgs, null);
    }

    public static String getMessage(String msg, Object[] msgArgs, String basicText) {
        return StringUtils.getMessage(getOriginMessage(msg, basicText), msgArgs);
    }

    private static String getOriginMessage(String msgKey, String basicText) {

        // 하드코딩
        Locale locale = org.springframework.util.StringUtils.parseLocaleString("ko");

        if (org.springframework.util.StringUtils.isEmpty(msgKey)) {
            if (org.springframework.util.StringUtils.isEmpty(basicText)) {
                return "";
            } else {
                return basicText;
            }
        }
        if (org.springframework.util.StringUtils.isEmpty(basicText)) {
            basicText = "";
        }
        try {
            return messageSource.getMessage(msgKey, null, locale);
        } catch (NoSuchMessageException e) {
            return basicText;
        }
    }



}
