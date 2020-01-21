package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.OrganizationSection;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(OrganizationSection.Position position) {
        return DateUtil.format(position.getDateFrom()) + " - " + DateUtil.format(position.getDateTo());
    }

}
