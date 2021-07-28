package cn.edu.buaa.crypto.encryption.tbbfkem.serparams;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import cn.edu.buaa.crypto.encryption.BloomFilterEncryption.Filter;
import it.unisa.dia.gas.jpbc.PairingParameters;

import java.io.File;
import java.util.Map;

public class TBBFKEMSecretKeyParameter {
    private Filter filter;
    private Map<String, PairingKeySerParameter> sk_bloom;
    private Map<String, PairingKeySerParameter> sk_time;
    private PairingParameters pairingParameters;

    public TBBFKEMSecretKeyParameter(PairingParameters pairingParameters, Filter filter,
                                     Map<String, PairingKeySerParameter> sk_bloom, Map<String, PairingKeySerParameter> sk_time) {
        this.pairingParameters = pairingParameters;
        this.filter= filter;
        this.sk_bloom = sk_bloom;
        this.sk_time = sk_time;
    }

    public Filter getFilter() {
        return filter;
    }

    public Map<String, PairingKeySerParameter> getSk_bloom() {
        return sk_bloom;
    }

    public Map<String, PairingKeySerParameter> getSk_time() {
        return sk_time;
    }

    public PairingParameters getPairingParameters() {
        return pairingParameters;
    }
}
