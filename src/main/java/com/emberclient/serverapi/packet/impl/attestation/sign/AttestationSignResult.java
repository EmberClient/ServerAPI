package com.emberclient.serverapi.packet.impl.attestation.sign;

public enum AttestationSignResult {
    SUCCESS(0),
    SIGNING_NOT_ALLOWED(1),
    SIGN_DATA_INVALID(2),
    USER_CANCELLED(3),
    KEY_DOES_NOT_EXIST(4),
    UNKNOWN_ERROR(5);

    private int code;

    AttestationSignResult(int code) {
        this.code = code;
    }

    public static AttestationSignResult fromCode(int code) {
        for (AttestationSignResult status : AttestationSignResult.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}
