package com.hedera;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

import java.io.IOException;
import java.util.logging.Logger;

public class BlockServer {
    private static final Logger logger = Logger.getLogger(BlockServer.class.getName());
    private final int port;
    private final Server server;

    public BlockServer(ServerBuilder serverBuilder, int port){
        this.port = port;
        BlockService blockService = new BlockService();
        server = serverBuilder.addService(blockService)
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    public BlockServer(int port) {
        this(ServerBuilder.forPort(port), port);
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void start() throws IOException{
        server.start();
        logger.info("Block app started at port: " + port);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        BlockServer blockServer = new BlockServer(50333);
        blockServer.start();
        blockServer.blockUntilShutdown();
    }
}
