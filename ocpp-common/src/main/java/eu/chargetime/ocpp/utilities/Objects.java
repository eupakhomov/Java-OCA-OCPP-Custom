package eu.chargetime.ocpp.utilities;

/*
 ubitricity.com - Java-OCA-OCPP

 MIT License

 Copyright (C) 2018 Evgeny Pakhomov <eugene.pakhomov@ubitricity.com>

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

import java.util.*;

/**
 * This class consists of {@code static} utility methods for operating
 * on objects.  These utilities include {@code null}-safe or {@code
 * null}-tolerant methods for computing the hash code of an object,
 * returning a string for an object, comparing two objects, etc.
 *
 * @author <a href=mailto:eugene.pakhomov@ubitricity.com>Eugene Pakhomov</a>
 */
public final class Objects {

    /**
     * Utility classes should have no instances.
     */
    private Objects() {
        throw new AssertionError("No Objects instances should exist");
    }

    /**
     * Returns {@code true} if the arguments are equal to each other and {@code false} otherwise. Consequently, if both
     * arguments are {@code null}, {@code true} is returned and if exactly one argument is {@code null}, {@code false}
     * is returned.  Otherwise, equality is determined by using the {@link Object#equals equals} method of the first
     * argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other and {@code false} otherwise
     * @see Object#equals(Object)
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }


    /**
     * Returns {@code true} if the arguments are deeply equal to each other
     * and {@code false} otherwise.
     *
     * Two {@code null} values are deeply equal.  If both arguments are
     * arrays, the algorithm in {@link Arrays#deepEquals(Object[],
     * Object[]) Arrays.deepEquals} is used to determine equality.
     * Otherwise, equality is determined by using the {@link
     * Object#equals equals} method of the first argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for deep equality
     * @return {@code true} if the arguments are deeply equal to each other
     * and {@code false} otherwise
     * @see Arrays#deepEquals(Object[], Object[])
     * @see Objects#equals(Object, Object)
     */
    public static boolean deepEquals(Object a, Object b) {
        if (a == b) {
            return true;
        } else if (a == null || b == null) {
            return false;
        } else {
            return Arrays.deepEquals(new Object[]{a}, new Object[]{b});
        }
    }

    /**
     * Returns the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument.
     *
     * @param o an object
     * @return the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument
     * @see Object#hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
     * Generates a hash code for a sequence of input values. The hash
     * code is generated as if all the input values were placed into an
     * array, and that array were hashed by calling {@link
     * Arrays#hashCode(Object[])}.
     *
     * <p>This method is useful for implementing {@link
     * Object#hashCode()} on objects containing multiple fields. For
     * example, if an object that has three fields, {@code x}, {@code
     * y}, and {@code z}, one could write:
     *
     * <blockquote><pre>
     * &#064;Override public int hashCode() {
     *     return Objects.hash(x, y, z);
     * }
     * </pre></blockquote>
     *
     * <b>Warning: When a single object reference is supplied, the returned
     * value does not equal the hash code of that object reference.</b> This
     * value can be computed by calling {@link #hashCode(Object)}.
     *
     * @param values the values to be hashed
     * @return a hash value of the sequence of input values
     * @see Arrays#hashCode(Object[])
     */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     * Creates shallow copy of input array. Returns null in case when null is passed as argument.
     *
     * @param array the array to be copied
     * @param <T> type of passed array elements
     * @return copy of input array or null in case when null is passed as argument
     */
    public static <T> T[] clone(T[] array) { return array == null ? null : Arrays.copyOf(array, array.length); }

    /**
     * Creates shallow copy of input array. Returns null in case when null is passed as argument.
     *
     * @param array the array to be copied
     * @return copy of input array or null in case when null is passed as argument
     */
    public static byte[] clone(byte[] array) { return array == null ? null : Arrays.copyOf(array, array.length); }

    /**
     * Returns helper to generate string representation of given input object.
     *
     * @param self the object to be represented as string
     * @return helper to generate string representation of given input object
     */
    public static Objects.ToStringHelper toStringHelper(Object self) {
        return new Objects.ToStringHelper(self);
    }

    /**
     * Returns helper to generate string representation of given input object.
     *
     * @param self the object to be represented as string
     * @param outputFullDetails the flag to be set to output all elements of container (list, set, queue, map) or array of objects
     * @return helper to generate string representation of given input object
     */
    public static Objects.ToStringHelper toStringHelper(Object self, boolean outputFullDetails) {
        return new Objects.ToStringHelper(self, outputFullDetails);
    }

    /**
     * Returns helper to generate string representation of given input class.
     *
     * @param clazz the class to be represented as string
     * @return helper to generate string representation of given input class
     */
    public static Objects.ToStringHelper toStringHelper(Class<?> clazz) {
        return new Objects.ToStringHelper(clazz);
    }

    /**
     * Returns helper to generate string representation of given input class.
     *
     * @param clazz the class to be represented as string
     * @param outputFullDetails the flag to be set to output all elements of container (list, set, queue, map) or array of objects
     * @return helper to generate string representation of given input class
     */
    public static Objects.ToStringHelper toStringHelper(Class<?> clazz, boolean outputFullDetails) {
        return new Objects.ToStringHelper(clazz, outputFullDetails);
    }

    /**
     * Returns helper to generate string representation of class with given className.
     *
     * @param className the name of class to be represented as string
     * @return helper to generate string representation of class with given className
     */
    public static Objects.ToStringHelper toStringHelper(String className) {
        return new Objects.ToStringHelper(className);
    }

    /**
     * Returns helper to generate string representation of class with given className.
     *
     * @param className the name of class to be represented as string
     * @param outputFullDetails the flag to be set to output all elements of container (list, set, queue, map) or array of objects
     * @return helper to generate string representation of class with given className
     */
    public static Objects.ToStringHelper toStringHelper(String className, boolean outputFullDetails) {
        return new Objects.ToStringHelper(className, outputFullDetails);
    }

    /**
     * Simple decorator to encapsulate actual toString helper implementation.
     * If array of primitives passed as input parameter to {@link Objects.ToStringHelper#add} function
     * when if array length more than {@link Objects.ToStringHelper#MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS}
     * then only length of that array will be written in output. If any container (list, set, queue, map)
     * or array of objects passed as input parameter to {@link Objects.ToStringHelper#add} function then only
     * size of that container (array of objects) will be written in output.
     */
    public static final class ToStringHelper {

        public static final int MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS = 32;
        public static final String FIELD_NAME_LENGTH_POSTFIX = ".length";
        public static final String FIELD_NAME_SIZE_POSTFIX = ".size";
        public static final String SECURE_FIELD_VALUE_REPLACEMENT = "********";

        private final boolean outputFullDetails;
        private final MoreObjects.ToStringHelper helperImplementation;

        private ToStringHelper(MoreObjects.ToStringHelper helperImplementation, boolean outputFullDetails) {
            this.helperImplementation = helperImplementation;
            this.outputFullDetails = outputFullDetails;
        }

        private ToStringHelper(Object self) {
            this(MoreObjects.toStringHelper(self), false);
        }

        private ToStringHelper(Class<?> clazz) {
            this(MoreObjects.toStringHelper(clazz), false);
        }

        private ToStringHelper(String className) {
            this(MoreObjects.toStringHelper(className), false);
        }

        private ToStringHelper(Object self, boolean outputFullDetails) {
            this(MoreObjects.toStringHelper(self), outputFullDetails);
        }

        private ToStringHelper(Class<?> clazz, boolean outputFullDetails) {
            this(MoreObjects.toStringHelper(clazz), outputFullDetails);
        }

        private ToStringHelper(String className, boolean outputFullDetails) {
            this(MoreObjects.toStringHelper(className), outputFullDetails );
        }

        /**
         * Exclude from output fields with null value.
         *
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper omitNullValues() {
            helperImplementation.omitNullValues();
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, Object value) {
            helperImplementation.add(name, value);
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, boolean value) {
            helperImplementation.add(name, String.valueOf(value));
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, char value) {
            helperImplementation.add(name, String.valueOf(value));
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, double value) {
            helperImplementation.add(name, String.valueOf(value));
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, float value) {
            helperImplementation.add(name, String.valueOf(value));
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, int value) {
            helperImplementation.add(name, String.valueOf(value));
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, long value) {
            helperImplementation.add(name, String.valueOf(value));
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, List<?> value) {
            return addCollection(name, value);
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, Set<?> value) {
            return addCollection(name, value);
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, Map<?, ?> value) {
            return addMap(name, value);
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, Queue<?> value) {
            return addCollection(name, value);
        }

        private  Objects.ToStringHelper addCollection(String name, Collection<?> value) {
            if(value != null && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_SIZE_POSTFIX, value.size());
            } else {
                helperImplementation.add(name, value);
            }
            return this;
        }

        private  Objects.ToStringHelper addMap(String name, Map<?, ?> value) {
            if(value != null && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_SIZE_POSTFIX, value.size());
            } else {
                helperImplementation.add(name, value);
            }
            return this;
        }


        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @param <T> type of passed array elements
         * @return ToStringHelper instance
         */
        public <T> Objects.ToStringHelper add(String name, T[] value) {
            if(value != null && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, byte[] value) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, boolean[] value) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, char[] value) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, double[] value) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, float[] value) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, int[] value ) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper add(String name, long[] value) {
            if(value != null && value.length > MAXIMUM_ARRAY_SIZE_TO_OUTPUT_DETAILS  && !outputFullDetails) {
                helperImplementation.add(name + FIELD_NAME_LENGTH_POSTFIX, value.length);
            } else {
                helperImplementation.add(name, Arrays.toString(value));
            }
            return this;
        }

        /**
         * Add field name and mask instead of real value to output.
         * It's safe to pass null as value.
         *
         * @param name field name
         * @param value field value
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addSecure(String name, String value) {
            value = SECURE_FIELD_VALUE_REPLACEMENT;
            helperImplementation.add(name, value);
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(Object value) {
            helperImplementation.addValue(value);
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(boolean value) {
            helperImplementation.addValue(String.valueOf(value));
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(char value) {
            helperImplementation.addValue(String.valueOf(value));
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(double value) {
            helperImplementation.addValue(String.valueOf(value));
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(float value) {
            helperImplementation.addValue(String.valueOf(value));
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(int value) {
            helperImplementation.addValue(String.valueOf(value));
            return this;
        }

        /**
         * Add value to output.
         *
         * @param value to add to output
         * @return ToStringHelper instance
         */
        public Objects.ToStringHelper addValue(long value) {
            helperImplementation.addValue(String.valueOf(value));
            return this;
        }

        /**
         * Returns resulting output string.
         *
         * @return resulting output string
         */
        public String toString() {
            return helperImplementation.toString();
        }
    }
}
