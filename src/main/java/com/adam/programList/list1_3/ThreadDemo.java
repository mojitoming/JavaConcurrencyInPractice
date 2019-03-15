package com.adam.programList.list1_3;

import java.math.BigDecimal;

/**
 * Annotation:
 * 清单 1-3  演示 Thread Joining
 *
 * @Author: Adam Ming
 * @Date: Sep 18, 2018 at 4:27:40 PM
 */
public class ThreadDemo {
    // constant used in pi computation
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);

    // rounding mode to use during pi computation
    private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;

    private static BigDecimal result;

    /*
     * Annotation:
     * Compute the value of pi to the specified number of digits after the
     * decimal point.The value is computed using Machin's formula:
     *
     * pi/4 = 4*arctan(1/5) - arctan(1/239)
     *
     * and a power series expansion of arctan(x) to sufficient precision.
     *
     * @Author: Adam Ming
     * @Date: Sep 18, 2018 at 4:30:13 PM
     */
    public static void main(String[] args) {
        /*
        // old way
        Runnable r = new Runnable() {
            @Override
            public void run() {
                result = computePi(50000);
            }
        };*/

        // lambda
        Runnable r = () -> result = computePi(50000);

        Thread t = new Thread(r);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            // Should never arrive here because interrupt() is never called.
            e.printStackTrace();
        }

        System.out.println(result);
    }

    /*
     * Compute the value of pi to the specified number of digits after the
     * decimal point. The value is computed using Machin's formula:
     *
     * pi/4 = 4*arctan(1/5) - arctan(1/239)
     *
     * and a power series expansion of arctan(x) to sufficient precision.
     */
    private static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        BigDecimal pi = arctan1_5.multiply(FOUR).
                subtract(arctan1_239).multiply(FOUR);

        return pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    /*
     * Compute the value, in radians, of the arctangent of the the inverse of
     * the supplied integer to the specified number of digits after the
     * decimal point. The value is computed using the power series
     * expansion for the arc tangent:
     *
     * arctan(x) = x - (x^3)/3 + (x^5)/5 - (x^7)/7 + (x^9)/9 ...
     */
    private static BigDecimal arctan(int inverseX, int scale) {
        BigDecimal result, numer, term;
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
        numer = BigDecimal.ONE.divide(invX, scale, roundingMode);
        result = numer;
        int i = 1;
        do {
            numer = numer.divide(invX2, scale, roundingMode);
            int denom = 2 * i + 1;
            term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);
            if ((i % 2) != 0) {
                result = result.subtract(term);
            } else {
                result = result.add(term);
            }
            i++;
        } while (term.compareTo(BigDecimal.ZERO) != 0);

        return result;
    }
}
