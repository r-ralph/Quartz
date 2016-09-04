/*
 * Copyright 2016 Ralph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.ralph.quartz;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Utility class
 */
public class QuartzUtil {
    /**
     * Store object into Bundle with proper type
     *
     * @param bundle Bundle
     * @param obj    Object will be stored
     * @throws IllegalArgumentException If obj can't be stored into Bundle
     */
    public static void put(@NonNull Bundle bundle, @NonNull String key, @Nullable Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof boolean[]) {
            bundle.putBooleanArray(key, (boolean[]) obj);
        } else if (obj instanceof Boolean[]) {
            bundle.putBooleanArray(key, convert((Boolean[]) obj));
        } else if (obj instanceof Byte) {
            bundle.putByte(key, (Byte) obj);
        } else if (obj instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) obj);
        } else if (obj instanceof Byte[]) {
            bundle.putByteArray(key, convert((Byte[]) obj));
        } else if (obj instanceof Short) {
            bundle.putShort(key, (Short) obj);
        } else if (obj instanceof short[]) {
            bundle.putShortArray(key, (short[]) obj);
        } else if (obj instanceof Short[]) {
            bundle.putShortArray(key, convert((Short[]) obj));
        } else if (obj instanceof Integer) {
            bundle.putInt(key, (Integer) obj);
        } else if (obj instanceof int[]) {
            bundle.putIntArray(key, (int[]) obj);
        } else if (obj instanceof Integer[]) {
            bundle.putIntArray(key, convert((Integer[]) obj));
        } else if (obj instanceof Long) {
            bundle.putLong(key, (Long) obj);
        } else if (obj instanceof long[]) {
            bundle.putLongArray(key, (long[]) obj);
        } else if (obj instanceof Long[]) {
            bundle.putLongArray(key, convert((Long[]) obj));
        } else if (obj instanceof Float) {
            bundle.putFloat(key, (Float) obj);
        } else if (obj instanceof float[]) {
            bundle.putFloatArray(key, (float[]) obj);
        } else if (obj instanceof Float[]) {
            bundle.putFloatArray(key, convert((Float[]) obj));
        } else if (obj instanceof Double) {
            bundle.putDouble(key, (Double) obj);
        } else if (obj instanceof double[]) {
            bundle.putDoubleArray(key, (double[]) obj);
        } else if (obj instanceof Double[]) {
            bundle.putDoubleArray(key, convert((Double[]) obj));
        } else if (obj instanceof Character) {
            bundle.putChar(key, (Character) obj);
        } else if (obj instanceof char[]) {
            bundle.putCharArray(key, (char[]) obj);
        } else if (obj instanceof Character[]) {
            bundle.putCharArray(key, convert((Character[]) obj));
        } else if (obj instanceof String) {
            bundle.putString(key, (String) obj);
        } else if (obj instanceof String[]) {
            bundle.putStringArray(key, (String[]) obj);
        } else if (obj instanceof CharSequence) {
            bundle.putCharSequence(key, (CharSequence) obj);
        } else if (obj instanceof Bundle) {
            bundle.putBundle(key, (Bundle) obj);
        } else if (obj instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) obj);
        } else if (obj instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) obj);
        } else if (isParcelableSparseArray(obj)) {
            //noinspection unchecked
            bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) obj);
        } else if (isStringArrayList(obj)) {
            //noinspection unchecked
            bundle.putStringArrayList(key, (ArrayList<String>) obj);
        } else if (isIntegerArrayList(obj)) {
            //noinspection unchecked
            bundle.putIntegerArrayList(key, (ArrayList<Integer>) obj);
        } else if (isParcelableArrayList(obj)) {
            //noinspection unchecked
            bundle.putParcelableArrayList(key, (ArrayList<Parcelable>) obj);
        } else if (obj instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) obj);
        } else {
            throw new IllegalArgumentException(obj.getClass() + " is not supported type.");
        }
    }


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static <T> T get(@NonNull Bundle bundle, @NonNull String key, @NonNull Class<T> klass) {
        T object;
        if (klass == Boolean[].class) {
            object = (T) convert(bundle.getBooleanArray(key));
        } else if (klass == Byte[].class) {
            object = (T) convert(bundle.getByteArray(key));
        } else if (klass == Short[].class) {
            object = (T) convert(bundle.getShortArray(key));
        } else if (klass == Integer[].class) {
            object = (T) convert(bundle.getIntArray(key));
        } else if (klass == Long[].class) {
            object = (T) convert(bundle.getLongArray(key));
        } else if (klass == Float[].class) {
            object = (T) convert(bundle.getFloatArray(key));
        } else if (klass == Double[].class) {
            object = (T) convert(bundle.getDoubleArray(key));
        } else if (klass == Character[].class) {
            object = (T) convert(bundle.getCharArray(key));
        } else {
            object = (T) bundle.get(key);
        }
        return object;
    }

    /**
     * Check object is {@literal ArrayList<? extends Parcelable>}
     *
     * @param obj Object to be checked
     * @return <code>true</code> if object is {@literal ArrayList<? extends Parcelable>}
     */
    private static boolean isParcelableArrayList(@NonNull Object obj) {
        if (!(obj instanceof ArrayList)) {
            return false;
        }
        ArrayList arrayList = ((ArrayList) obj);
        for (Object element : arrayList) {
            if (!(element instanceof Parcelable)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check object is {@literal ArrayList<Integer>}
     *
     * @param obj Object to be checked
     * @return <code>true</code> if object is {@literal ArrayList<Integer>}
     */
    private static boolean isIntegerArrayList(@NonNull Object obj) {
        if (!(obj instanceof ArrayList)) {
            return false;
        }
        ArrayList arrayList = ((ArrayList) obj);
        for (Object element : arrayList) {
            if (!(element instanceof Integer)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check object is {@literal ArrayList<String>}
     *
     * @param obj Object to be checked
     * @return <code>true</code> if object is {@literal ArrayList<String>}
     */
    private static boolean isStringArrayList(@NonNull Object obj) {
        if (!(obj instanceof ArrayList)) {
            return false;
        }
        ArrayList arrayList = ((ArrayList) obj);
        for (Object element : arrayList) {
            if (!(element instanceof String)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check object is {@literal SparseArray<? extends Parcelable>}
     *
     * @param obj Object to be checked
     * @return <code>true</code> if object is {@literal SparseArray<? extends Parcelable>}
     */
    private static boolean isParcelableSparseArray(@NonNull Object obj) {
        if (!(obj instanceof SparseArray)) {
            return false;
        }
        SparseArray sparseArray = (SparseArray) obj;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            if (!(sparseArray.valueAt(i) instanceof Parcelable)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert {@literal Boolean[]} to {@literal boolean[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static boolean[] convert(@NonNull Boolean[] array) {
        boolean[] primitiveArray = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Byte[]} to {@literal byte[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static byte[] convert(@NonNull Byte[] array) {
        byte[] primitiveArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Short[]} to {@literal short[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static short[] convert(@NonNull Short[] array) {
        short[] primitiveArray = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Integer[]} to {@literal int[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static int[] convert(@NonNull Integer[] array) {
        int[] primitiveArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Long[]} to {@literal long[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static long[] convert(@NonNull Long[] array) {
        long[] primitiveArray = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Float[]} to {@literal float[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static float[] convert(@NonNull Float[] array) {
        float[] primitiveArray = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Double[]} to {@literal double[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static double[] convert(@NonNull Double[] array) {
        double[] primitiveArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal Character[]} to {@literal char[]}
     *
     * @param array Object to be converted
     * @return Converted array
     */
    private static char[] convert(@NonNull Character[] array) {
        char[] primitiveArray = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = array[i];
        }
        return primitiveArray;
    }

    /**
     * Convert {@literal boolean[]} to {@literal Boolean[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Boolean[] convert(@NonNull boolean[] primitiveArray) {
        Boolean[] array = new Boolean[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal byte[]} to {@literal Byte[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Byte[] convert(@NonNull byte[] primitiveArray) {
        Byte[] array = new Byte[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal short[]} to {@literal Short[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Short[] convert(@NonNull short[] primitiveArray) {
        Short[] array = new Short[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal int[]} to {@literal Integer[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Integer[] convert(@NonNull int[] primitiveArray) {
        Integer[] array = new Integer[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal long[]} to {@literal Long[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Long[] convert(@NonNull long[] primitiveArray) {
        Long[] array = new Long[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal float[]} to {@literal Float[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Float[] convert(@NonNull float[] primitiveArray) {
        Float[] array = new Float[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal double[]} to {@literal Double[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Double[] convert(@NonNull double[] primitiveArray) {
        Double[] array = new Double[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }

    /**
     * Convert {@literal char[]} to {@literal Character[]}
     *
     * @param primitiveArray Object to be converted
     * @return Converted array
     */
    private static Character[] convert(@NonNull char[] primitiveArray) {
        Character[] array = new Character[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            array[i] = primitiveArray[i];
        }
        return array;
    }
}
