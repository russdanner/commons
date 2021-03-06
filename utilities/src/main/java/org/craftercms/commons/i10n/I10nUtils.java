/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.commons.i10n;

import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class for localization stuff.
 *
 * @author avasquez
 */
public class I10nUtils {

    private I10nUtils() {
    }

    /**
     * Returns a formatted, localized message according to the specified resource bundle and key.
     *
     * @param bundle the resource bundle where the message format should be
     * @param key    the key of the message format
     * @param args   the args of the message format
     * @return the formatted, localized message
     */
    public static String getLocalizedMessage(ResourceBundle bundle, String key, Object... args) {
        String pattern;
        try {
            pattern = bundle.getString(key);
        } catch (MissingResourceException e) {
            pattern = key;
        }

        if (ArrayUtils.isNotEmpty(args)) {
            return MessageFormat.format(pattern, args);
        } else {
            return pattern;
        }
    }

}
