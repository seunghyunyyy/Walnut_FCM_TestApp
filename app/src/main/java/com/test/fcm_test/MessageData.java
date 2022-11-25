package com.test.fcm_test;

public class MessageData {
    private String opcode;
    private Long tokenId;
    private Long msgId;

    public MessageData(String opcode, Long tokenId, Long msgId) {
        this.opcode = opcode;
        this.tokenId = tokenId;
        this.msgId = msgId;
    }

    public String getOpcode() {
        return this.opcode;
    }
    public Long getTokenId() {
        return this.tokenId;
    }
    public Long getMsgId() {
        return this.msgId;
    }
}
