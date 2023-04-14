package com.emberclient.serverapi.packet.impl.attestation.register;

public enum AttestationRegisterResult {
    SUCCESS(0),
    SIGNING_NOT_ALLOWED(1),
    USER_CANCELLED(2),
    UNKNOWN_ERROR(3);

    private int code;

    AttestationRegisterResult(int code) {
        this.code = code;
    }

    public static AttestationRegisterResult fromCode(int code) {
        for (AttestationRegisterResult status : AttestationRegisterResult.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}
