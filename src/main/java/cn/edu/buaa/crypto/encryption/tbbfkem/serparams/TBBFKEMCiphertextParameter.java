package cn.edu.buaa.crypto.encryption.tbbfkem.serparams;

import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingParameters;

import java.util.List;
import java.util.Map;


public class TBBFKEMCiphertextParameter {
    private byte[] c;
    private Map<String, PairingCipherSerParameter> encapsulatedKeys;

    public byte[] getC() {
        return c;
    }

    public Map<String, PairingCipherSerParameter> getEncapsulatedKeys() {
        return encapsulatedKeys;
    }

    public TBBFKEMCiphertextParameter(byte[] c, Map<String, PairingCipherSerParameter> encapsulatedKeys) {
        this.c = c;
        this.encapsulatedKeys = encapsulatedKeys;
    }
}
