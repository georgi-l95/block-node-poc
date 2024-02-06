package com.hedera;

import com.hedera.block.node.api.proto.java.BlockServiceGrpc;
import com.hedera.block.node.api.proto.java.BlocksPutIfAbsentRequest;
import com.hedera.block.node.api.proto.java.BlocksPutIfAbsentResponse;
import com.hedera.block.node.api.proto.java.BlocksPutIfAbsentResponseCode;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class BlockService extends BlockServiceGrpc.BlockServiceImplBase {
    private static final Logger logger = Logger.getLogger(BlockService.class.getName());
    public BlockService(){

    }

    @Override
    public StreamObserver<BlocksPutIfAbsentRequest> blocksPutIfAbsent(StreamObserver<BlocksPutIfAbsentResponse> responseObserver) {
        return new StreamObserver<BlocksPutIfAbsentRequest>() {
            @Override
            public void onNext(BlocksPutIfAbsentRequest request) {
                logger.info("Received request: " + request);
                BlocksPutIfAbsentResponseCode responseCode = Math.random() > 0.5 ? BlocksPutIfAbsentResponseCode.BLOCKS_PUT_IF_ABSENT_SUCCESS : BlocksPutIfAbsentResponseCode.BLOCKS_PUT_IF_ABSENT_BLOCK_ALREADY_EXISTS;
                BlocksPutIfAbsentResponse response = BlocksPutIfAbsentResponse.newBuilder()
                                .setStatus(responseCode).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.warning("Warning " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
//  rpc RateLaptop(stream RateLaptopRequest) returns (stream RateLaptopResponse) {
//  rpc blocksPutIfAbsent (stream BlocksPutIfAbsentRequest) returns (BlocksPutIfAbsentResponse);