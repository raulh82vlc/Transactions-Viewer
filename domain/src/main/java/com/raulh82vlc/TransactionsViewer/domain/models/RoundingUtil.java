/*
 * Copyright (C) 2017 Raul Hernandez Lopez @raulh82vlc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raulh82vlc.TransactionsViewer.domain.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rounding final class util
 *
 * @author Raul Hernandez Lopez.
 */

public final class RoundingUtil {

    private RoundingUtil() {
    }

    /**
     * Rounding functionality
     *
     * @param value       value to round
     * @param roundDigits number of digits to show on the decimals
     * @return rounded value
     */
    public static BigDecimal round(BigDecimal value, int roundDigits) {
        if (roundDigits < 0) throw new IllegalArgumentException();
        return value.setScale(roundDigits, RoundingMode.HALF_EVEN);
    }
}
