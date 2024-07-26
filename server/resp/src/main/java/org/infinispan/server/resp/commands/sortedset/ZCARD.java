package org.infinispan.server.resp.commands.sortedset;

import java.util.List;
import java.util.concurrent.CompletionStage;

import org.infinispan.multimap.impl.EmbeddedMultimapSortedSetCache;
import org.infinispan.server.resp.Resp3Handler;
import org.infinispan.server.resp.RespCommand;
import org.infinispan.server.resp.RespRequestHandler;
import org.infinispan.server.resp.commands.Resp3Command;
import org.infinispan.server.resp.serialization.Resp3Response;

import io.netty.channel.ChannelHandlerContext;

/**
 * Returns the sorted set number of elements.
 *
 * @since 15.0
 * @see <a href="https://redis.io/commands/zcard">Redis Documentation</a>
 */
public class ZCARD extends RespCommand implements Resp3Command {

   public ZCARD() {
      super(2, 1, 1, 1);
   }

   @Override
   public CompletionStage<RespRequestHandler> perform(Resp3Handler handler,
                                                      ChannelHandlerContext ctx,
                                                      List<byte[]> arguments) {

      byte[] name = arguments.get(0);
      EmbeddedMultimapSortedSetCache<byte[], ?> sortedSetCache = handler.getSortedSeMultimap();
      return handler.stageToReturn(sortedSetCache.size(name), ctx, Resp3Response.INTEGER);
   }
}
