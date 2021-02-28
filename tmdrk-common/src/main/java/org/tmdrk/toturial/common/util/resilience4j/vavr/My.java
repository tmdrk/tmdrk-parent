package org.tmdrk.toturial.common.util.resilience4j.vavr;

import io.vavr.Tuple;
import io.vavr.Tuple1;
import io.vavr.match.annotation.Patterns;
import io.vavr.match.annotation.Unapply;

/**
 * My
 *
 * @author Jie.Zhou
 * @date 2021/2/19 16:55
 */
@Patterns
public class My {
    @Unapply
    static <T> Tuple1<T> Optional(java.util.Optional<T> optional) {
        return Tuple.of(optional.orElse(null));
    }
}
