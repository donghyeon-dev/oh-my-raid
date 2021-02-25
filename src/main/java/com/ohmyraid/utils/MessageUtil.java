package com.ohmyraid.utils;

import com.ohmyraid.common.suport.StaticContextAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.util.Locale;

public class MessageUtil {
    static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    private static MessageSource messageSource = StaticContextAccessor.getBean(ReloadableResourceBundleMessageSource.class);

    public static String getMessage(String msgId) {
        return getMessage(msgId, new String[] {});
    }

    public static String getMessage(String msgId, Object msgArgs) {
        return getMessage(msgId, new Object[] {msgArgs}, null);
    }

    public static String getMessage(String msgId, Object[] msgArgs) {
        return getMessage(msgId, msgArgs, null);
    }

    public static String getMessage(String msgId, Object[] msgArgs, String basicText) {
        return StringUtil.getMessage(getOriginMessage(msgId, basicText), msgArgs);
    }

    private static String getOriginMessage(String msgKey, String basicText) {

        // 하드코딩
        Locale locale = StringUtils.parseLocaleString("ko");

        if (org.springframework.util.StringUtils.isEmpty(msgKey)) {
            if (org.springframework.util.StringUtils.isEmpty(basicText)) {
                return "";
            } else {
                return basicText;
            }
        }
        if (StringUtils.isEmpty(basicText)) {
            basicText = "";
        }
        try {
            return messageSource.getMessage(msgKey, null, locale);
        } catch (NoSuchMessageException e) {
            return basicText;
        }
    }



}
