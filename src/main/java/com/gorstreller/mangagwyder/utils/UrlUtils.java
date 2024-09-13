package com.gorstreller.mangagwyder.utils;

import com.gorstreller.mangagwyder.constants.RegularExpressions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.gorstreller.mangagwyder.constants.ViewsConstants.LOGO_SAMPLE;
import static com.gorstreller.mangagwyder.constants.ViewsConstants.VIEWS_PATH;

@Component
public class UrlUtils {

    @Value("${s3.prefix}")
    private String s3Prefix;

    public String createLogoPath(String baseUrl, String mangaTitle) {
        return baseUrl + s3Prefix + String.format(VIEWS_PATH,
                mangaTitle,
                String.format(LOGO_SAMPLE, mangaTitle.replace(StringUtils.SPACE, RegularExpressions.UNDERSCORE)));
    }
}
