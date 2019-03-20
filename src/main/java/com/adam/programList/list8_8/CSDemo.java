package com.adam.programList.list8_8;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.*;

/**
 * Annotation:
 * 借助 Completion Service 计算欧拉数
 *
 * @Author: Adam Ming
 * @Date: Mar 20, 2019 at 9:00:26 PM
 */
public class CSDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletionService<BigDecimal> cs = new ExecutorCompletionService<>(es);
        cs.submit(new CalculateE(17));
        cs.submit(new CalculateE(170));

        Future<BigDecimal> result = cs.take();
        System.out.println(result.get());
        System.out.println();
        result = cs.take();
        System.out.println(result.get());
        System.out.println();
        es.shutdown();
    }
}

@SuppressWarnings("Duplicates")
class CalculateE implements Callable<BigDecimal> {
    final int lastIter;

    public CalculateE(int lastIter) {
        this.lastIter = lastIter;
    }

    @Override
    public BigDecimal call() throws Exception {
        MathContext mc = new MathContext(100, RoundingMode.HALF_UP);
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i <= lastIter; i++) {
            BigDecimal factorial = factorial(new BigDecimal(i));
            BigDecimal res = BigDecimal.ONE.divide(factorial, mc);
            result = result.add(res);
        }

        return result;
    }

    private BigDecimal factorial(BigDecimal n) {
        if (n.equals(BigDecimal.ZERO)) {
            return BigDecimal.ONE;
        } else {
            return n.multiply(factorial(n.subtract(BigDecimal.ONE)));
        }
    }
}
