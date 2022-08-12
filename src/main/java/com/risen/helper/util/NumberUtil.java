package com.risen.helper.util;

import org.springframework.util.ObjectUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 15:45
 */
public class NumberUtil {

    public static final Integer DEAULT_INTGER = 0;
    public static final Float DEAULT_FLOAT = 0f;
    public static final Double DEAULT_DOUBLE = 0d;
    public static final Long DEAULT_LONG = 0L;
    public static final String HOLDEFFECTIVE_0 = "####";
    public static final String HOLDEFFECTIVE_1 = "####.#";
    public static final String HOLDEFFECTIVE_2 = "####.##";
    public static final String HOLDEFFECTIVE_3 = "####.###";
    public static final String HOLDEFFECTIVE_4 = "####.####";

    public static Map<Integer, String> FLOAT_HOLD_MAP = new HashMap<>();

    static {
        FLOAT_HOLD_MAP.put(0, HOLDEFFECTIVE_0);
        FLOAT_HOLD_MAP.put(1, HOLDEFFECTIVE_1);
        FLOAT_HOLD_MAP.put(2, HOLDEFFECTIVE_2);
        FLOAT_HOLD_MAP.put(3, HOLDEFFECTIVE_3);
        FLOAT_HOLD_MAP.put(4, HOLDEFFECTIVE_4);
    }

    public static boolean isInt(String param) {
        boolean flag = true;
        try {
            Integer.parseInt(param);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNull(Number number) {
        return ObjectUtils.isEmpty(number);
    }


    public static boolean isAtLeastOneNull(Number... number) {
        if (ObjectUtils.isEmpty(number)) {
            return true;
        }
        boolean isNull = false;
        for (Number num : number) {
            if (isNull(num)) {
                isNull = true;
                break;
            }
        }
        return isNull;
    }

    public static Float getFloat(String data) {
        Float s = null;
        try {
            s = Float.parseFloat(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static Boolean isMiddle(Long l1, Long l2, Long l3) {
        return l1 >= l2 && l1 <= l3;
    }

    public static Boolean isMiddle(Float l1, Float l2, Float l3) {
        return l1 >= l2 && l1 <= l3;
    }

    public static Boolean isMiddle(Double l1, Double l2, Double l3) {
        return l1 >= l2 && l1 <= l3;
    }

    public static Boolean isMiddle(Integer l1, Integer l2, Integer l3) {
        return l1 >= l2 && l1 <= l3;
    }

    public static <U extends Number> U divide(Number e1, Number e2, Class<U> u, Integer append) {
        if (PredicateUtil.isAnyEmpty(e1, e2)) {
            return null;
        }
        if (PredicateUtil.isZero(e1) || PredicateUtil.isZero(e2)) {
            return null;
        }
        if (!PredicateUtil.isNotEmpty(append)) {
            append = 0;
        }
        return DataTranferUtil.tansferTo((e1.doubleValue() / e2.doubleValue()) + append, u, 0);
    }

    public static int intValue(Number value) {
        return PredicateUtil.isNotEmpty(value) ? value.intValue() : 0;
    }

    public static Double doubleValue(Number value) {
        return PredicateUtil.isNotEmpty(value) ? value.doubleValue() : 0D;
    }

    public static Float floatValue(Number value) {
        return PredicateUtil.isNotEmpty(value) ? value.floatValue() : 0F;
    }

    public static Long longValue(Number value) {
        return PredicateUtil.isNotEmpty(value) ? value.longValue() : 0L;
    }


    public static <U extends Number> U add(Class<U> u, U source, U... n) {
        if (PredicateUtil.isAnyEmpty(u, source, n)) {
            return null;
        }
        if (Integer.class.isAssignableFrom(u)) {
            Integer result = intValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Integer index : (Integer[]) n) {
                    result += intValue(index);
                }
            }
            return (U) result;
        } else if (Float.class.isAssignableFrom(u)) {
            Float result = floatValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Float index : (Float[]) n) {
                    result += floatValue(index);
                }
            }
            return (U) result;
        } else if (Double.class.isAssignableFrom(u)) {
            Double result = doubleValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Double index : (Double[]) n) {
                    result += doubleValue(index);
                }
            }
            return (U) result;
        } else if (Long.class.isAssignableFrom(u)) {
            Long result = longValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Long index : (Long[]) n) {
                    result += longValue(index);
                }
            }
            return (U) result;
        }
        return (U) DEAULT_INTGER;
    }


    public static <U extends Number> U sub(Class<U> u, U source, U... n) {
        if (PredicateUtil.isAnyEmpty(u, source, n)) {
            return null;
        }
        if (Integer.class.isAssignableFrom(u)) {
            Integer result = intValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Integer index : (Integer[]) n) {
                    result -= intValue(index);
                }
            }
            return (U) result;
        } else if (Float.class.isAssignableFrom(u)) {
            Float result = floatValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Float index : (Float[]) n) {
                    result -= floatValue(index);
                }
            }
            return (U) result;
        } else if (Double.class.isAssignableFrom(u)) {
            Double result = doubleValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Double index : (Double[]) n) {
                    result -= doubleValue(index);
                }
            }
            return (U) result;
        } else if (Long.class.isAssignableFrom(u)) {
            Long result = longValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Long index : (Long[]) n) {
                    result -= longValue(index);
                }
            }
            return (U) result;
        }
        return (U) DEAULT_INTGER;
    }


    public static <U extends Number> U multi(Class<U> u, U source, U... n) {
        if (PredicateUtil.isAnyEmpty(u, source, n)) {
            return null;
        }
        if (Integer.class.isAssignableFrom(u)) {
            Integer result = intValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Integer index : (Integer[]) n) {
                    result *= intValue(index);
                }
            }
            return (U) result;
        } else if (Float.class.isAssignableFrom(u)) {
            Float result = floatValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Float index : (Float[]) n) {
                    result *= floatValue(index);
                }
            }
            return (U) result;
        } else if (Double.class.isAssignableFrom(u)) {
            Double result = doubleValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Double index : (Double[]) n) {
                    result *= doubleValue(index);
                }
            }
            return (U) result;
        } else if (Long.class.isAssignableFrom(u)) {
            Long result = longValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Long index : (Long[]) n) {
                    result *= longValue(index);
                }
            }
            return (U) result;
        }
        return (U) DEAULT_INTGER;
    }


    public static <U extends Number> U div(Class<U> u, U source, U... n) {
        if (PredicateUtil.isAnyEmpty(u, source, n)) {
            return null;
        }
        if (Integer.class.isAssignableFrom(u)) {
            Integer result = intValue(source);
            for (Integer index : (Integer[]) n) {
                if (PredicateUtil.isAnyEmpty(index) | PredicateUtil.isZero(index)) {
                    continue;
                }
                result /= intValue(index);
            }
            return (U) result;
        } else if (Float.class.isAssignableFrom(u)) {
            Float result = floatValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Float index : (Float[]) n) {
                    if (PredicateUtil.isAnyEmpty(index) | PredicateUtil.isZero(index)) {
                        continue;
                    }
                    result /= floatValue(index);
                }
            }
            return (U) result;
        } else if (Double.class.isAssignableFrom(u)) {
            Double result = doubleValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Double index : (Double[]) n) {
                    if (PredicateUtil.isAnyEmpty(index) | PredicateUtil.isZero(index)) {
                        continue;
                    }
                    result /= doubleValue(index);
                }
            }
            return (U) result;
        } else if (Long.class.isAssignableFrom(u)) {
            Long result = longValue(source);
            if (PredicateUtil.isNotEmpty(n)) {
                for (Long index : (Long[]) n) {
                    if (PredicateUtil.isAnyEmpty(index) | PredicateUtil.isZero(index)) {
                        continue;
                    }
                    result /= longValue(index);
                }
            }
            return (U) result;
        }
        return (U) DEAULT_INTGER;
    }


    public static String holdEffective(Number n, String e) {
        DecimalFormat df = new DecimalFormat(e);
        return df.format(n);
    }


    public static Integer filterOne(Integer s1, Integer s2) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : s2;
    }

    public static Float filterOne(Float s1, Float s2) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : s2;
    }

    public static Double filterOne(Double s1, Double s2) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : s2;
    }

    public static Long filterOne(Long s1, Long s2) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : s2;
    }


    public static Integer filterOne(Integer s1) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : DEAULT_INTGER;
    }

    public static Float filterOne(Float s1) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : DEAULT_FLOAT;
    }

    public static Double filterOne(Double s1) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : DEAULT_DOUBLE;
    }

    public static Long filterOne(Long s1) {
        return PredicateUtil.isNotEmpty(s1) ? s1 : DEAULT_LONG;
    }


    public static Integer parseStr2Integer(String s1) {
        Integer count = DEAULT_INTGER;
        try {
            count = PredicateUtil.isNotEmpty(s1) ? Integer.parseInt(s1) : DEAULT_INTGER;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Float parseStr2Float(String s1) {
        Float count = DEAULT_FLOAT;
        try {
            count = PredicateUtil.isNotEmpty(s1) ? Float.parseFloat(s1) : DEAULT_FLOAT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Double parseStr2Double(String s1) {
        Double count = DEAULT_DOUBLE;
        try {
            count = PredicateUtil.isNotEmpty(s1) ? Double.parseDouble(s1) : DEAULT_DOUBLE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Long parseStr2Long(String s1) {
        Long count = DEAULT_LONG;
        try {
            count = PredicateUtil.isNotEmpty(s1) ? Long.parseLong(s1) : DEAULT_LONG;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    public static String holdFloat(Number value, Integer holdValue, boolean needInto) {
        if (PredicateUtil.isNotEmpty(holdValue)) {
            DecimalFormat b = new DecimalFormat(FLOAT_HOLD_MAP.get(holdValue));
            if (!needInto) {
                b.setRoundingMode(RoundingMode.FLOOR);
            }
            String format = b.format(value);
            return format;
        } else {
            DecimalFormat b = new DecimalFormat(FLOAT_HOLD_MAP.get(0));
            if (!needInto) {
                b.setRoundingMode(RoundingMode.FLOOR);
            }
            return b.format(value);
        }
    }

}
