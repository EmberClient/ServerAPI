package com.emberclient.serverapi.packet.impl.attestation.sign;

public enum AttestationSignResult {
    SUCCESS,
    SIGNING_NOT_ALLOWED,
    SIGN_DATA_INVALID,
    USER_CANCELLED,
    KEY_DOES_NOT_EXIST,
    UNKNOWN_ERROR
}
