package com.sergtm;

import java.time.LocalTime;

public enum TimeOfDay {
    MORNING {
        @Override
        public LocalTime getLocalTime() {
            return LocalTime.of(8, 0);
        }
    },
    DAY {
        @Override
        public LocalTime getLocalTime() {
            return LocalTime.of(14, 0);
        }
    },
    EVENING {
        @Override
        public LocalTime getLocalTime() {
            return LocalTime.of(20, 0);
        }
    };

    public abstract LocalTime getLocalTime();
}
