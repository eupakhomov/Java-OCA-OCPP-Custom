package eu.chargetime.ocpp.utilities;

import java.util.Arrays;

/**
 * Internals for Objects.
 *
 * @author <a href=mailto:eugene.pakhomov@ubitricity.com>Eugene Pakhomov</a>
 */
public final class MoreObjects {
    public static <T> T firstNonNull(T first, T second) {
        return first != null ? first : java.util.Objects.requireNonNull(second);
    }

    public static MoreObjects.ToStringHelper toStringHelper(Object self) {
        return new MoreObjects.ToStringHelper(self.getClass().getSimpleName());
    }

    public static MoreObjects.ToStringHelper toStringHelper(Class<?> clazz) {
        return new MoreObjects.ToStringHelper(clazz.getSimpleName());
    }

    public static MoreObjects.ToStringHelper toStringHelper(String className) {
        return new MoreObjects.ToStringHelper(className);
    }

    private MoreObjects() {
    }

    public static final class ToStringHelper {
        private final String className;
        private final MoreObjects.ToStringHelper.ValueHolder holderHead;
        private MoreObjects.ToStringHelper.ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String className) {
            this.holderHead = new MoreObjects.ToStringHelper.ValueHolder();
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = java.util.Objects.requireNonNull(className);
        }

        public MoreObjects.ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public MoreObjects.ToStringHelper add(String name, Object value) {
            return this.addHolder(name, value);
        }

        public MoreObjects.ToStringHelper add(String name, boolean value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public MoreObjects.ToStringHelper add(String name, char value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public MoreObjects.ToStringHelper add(String name, double value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public MoreObjects.ToStringHelper add(String name, float value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public MoreObjects.ToStringHelper add(String name, int value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public MoreObjects.ToStringHelper add(String name, long value) {
            return this.addHolder(name, String.valueOf(value));
        }

        public MoreObjects.ToStringHelper addValue(Object value) {
            return this.addHolder(value);
        }

        public MoreObjects.ToStringHelper addValue(boolean value) {
            return this.addHolder(String.valueOf(value));
        }

        public MoreObjects.ToStringHelper addValue(char value) {
            return this.addHolder(String.valueOf(value));
        }

        public MoreObjects.ToStringHelper addValue(double value) {
            return this.addHolder(String.valueOf(value));
        }

        public MoreObjects.ToStringHelper addValue(float value) {
            return this.addHolder(String.valueOf(value));
        }

        public MoreObjects.ToStringHelper addValue(int value) {
            return this.addHolder(String.valueOf(value));
        }

        public MoreObjects.ToStringHelper addValue(long value) {
            return this.addHolder(String.valueOf(value));
        }

        public String toString() {
            boolean omitNullValuesSnapshot = this.omitNullValues;
            String nextSeparator = "";
            StringBuilder builder = (new StringBuilder(32)).append(this.className).append('{');

            for(MoreObjects.ToStringHelper.ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                Object value = valueHolder.value;
                if (!omitNullValuesSnapshot || value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";
                    if (valueHolder.name != null) {
                        builder.append(valueHolder.name).append('=');
                    }

                    if (value != null && value.getClass().isArray()) {
                        Object[] objectArray = new Object[]{value};
                        String arrayString = Arrays.deepToString(objectArray);
                        builder.append(arrayString, 1, arrayString.length() - 1);
                    } else {
                        builder.append(value);
                    }
                }
            }

            return builder.append('}').toString();
        }

        private MoreObjects.ToStringHelper.ValueHolder addHolder() {
            MoreObjects.ToStringHelper.ValueHolder valueHolder = new MoreObjects.ToStringHelper.ValueHolder();
            this.holderTail = this.holderTail.next = valueHolder;
            return valueHolder;
        }

        private MoreObjects.ToStringHelper addHolder(Object value) {
            MoreObjects.ToStringHelper.ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            return this;
        }

        private MoreObjects.ToStringHelper addHolder(String name, Object value) {
            MoreObjects.ToStringHelper.ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            valueHolder.name = java.util.Objects.requireNonNull(name);
            return this;
        }

        private static final class ValueHolder {
            String name;
            Object value;
            MoreObjects.ToStringHelper.ValueHolder next;

            private ValueHolder() {
            }
        }
    }
}
