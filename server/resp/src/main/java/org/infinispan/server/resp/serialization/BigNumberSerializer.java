package org.infinispan.server.resp.serialization;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.infinispan.server.resp.ByteBufPool;

/**
 * Represent signed integer values outside the 64-bit interval.
 *
 * <p>
 * Similar to {@link PrimitiveSerializer.IntegerSerializer}, but the prefix is the left parenthesis.
 * </p>
 *
 * @since 15.0
 * @author José Bolina
 */
final class BigNumberSerializer implements ResponseSerializer<BigInteger> {
   static final BigNumberSerializer INSTANCE = new BigNumberSerializer();

   @Override
   public void accept(BigInteger bigInteger, ByteBufPool alloc) {
      // FIXME: Find a way to write without string allocation.
      String value = bigInteger.toString(10);
      int size = 1 + value.length() + RespConstants.CRLF.length;

      // RESP: ([+|-]<number>\r\n
      alloc.acquire(size).writeByte(RespConstants.BIG_NUMBER)
            .writeBytes(value.getBytes(StandardCharsets.US_ASCII))
            .writeBytes(RespConstants.CRLF);
   }

   @Override
   public boolean test(Object object) {
      // Handle only non-fractional large values.
      return object instanceof BigInteger;
   }
}
