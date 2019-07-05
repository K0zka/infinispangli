package org.infinispan.rest.authentication;

import java.util.concurrent.CompletionStage;

import org.infinispan.rest.RestServer;
import org.infinispan.rest.framework.RestRequest;
import org.infinispan.rest.framework.RestResponse;

import io.netty.channel.ChannelHandlerContext;

/**
 * Authentication mechanism.
 *
 * @author Sebastian Łaskawiec
 */
public interface Authenticator {

   /**
    * Challenges specific {@link RestRequest} for authentication.
    *
    * @param request Request to be challenged.
    * @return a {@link RestResponse} wrapped in a {@link CompletionStage}
    */
   CompletionStage<RestResponse> challenge(RestRequest request, ChannelHandlerContext ctx);

   /**
    * Invoked by the {@link RestServer} on startup. Can perform additional configuration
    * @param restServer
    */
   default void init(RestServer restServer) {}
}
