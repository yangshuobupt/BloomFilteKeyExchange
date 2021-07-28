package cn.edu.buaa.crypto.encryption.tbbfkem.generators;

import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import cn.edu.buaa.crypto.encryption.BloomFilterEncryption.BloomFilter;
import cn.edu.buaa.crypto.encryption.fspibme.serparams.FSPIBMECiphertextSerParameter;
import cn.edu.buaa.crypto.encryption.fspibme.serparams.SigPublicParameter;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.HIBEBBG05Engine;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.serparams.HIBEBBG05HeaderSerParameter;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.serparams.HIBEBBG05PublicKeySerParameter;
import cn.edu.buaa.crypto.encryption.tbbfkem.Utils;
import cn.edu.buaa.crypto.encryption.tbbfkem.serparams.TBBFKEMCiphertextParameter;
import cn.edu.buaa.crypto.utils.PairingUtils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBBFKEMEncryptionGenerator {
    private HIBEBBG05PublicKeySerParameter publicKeyParameter;
    private HIBEBBG05Engine hibebbg05Engine;
    private String[] tau;
    private BloomFilter filter;

    public void init(HIBEBBG05Engine hibebbg05Engine, PairingKeySerParameter publicKey, BloomFilter filter, String... tau) {
        this.hibebbg05Engine = hibebbg05Engine;
        this.publicKeyParameter = (HIBEBBG05PublicKeySerParameter) publicKey;
        this.tau = tau;
        this.filter = filter;
    }


    public TBBFKEMCiphertextParameter computeEncapsulation() {
        Pairing pairing = PairingFactory.getPairing(publicKeyParameter.getParameters());
        int bloomFilterTreeDepth = filter.size();
        byte[] c = PairingUtils.hash(pairing.getG1().newRandomElement().toBytes());
        Element K = pairing.getGT().newRandomElement().getImmutable();
        int[] bloomPositions = BloomFilter.getBitPositions(new BigInteger(c), filter.getHashCount(), filter.size());
        Map<String, PairingCipherSerParameter> encapsulatedKeys = new HashMap<>();
        String timeSlot = Utils.getStrFromArray(tau);
        for (int bloomPosition : bloomPositions) {
            String keyIdentity = Integer.toBinaryString(bloomPosition);
            keyIdentity = timeSlot + Utils.padStringWithZeros(keyIdentity, bloomFilterTreeDepth);
            String[] keyIdentityArray = keyIdentity.split("");
            encapsulatedKeys.put(keyIdentity, hibebbg05Engine.encryption(publicKeyParameter, keyIdentityArray, K));
        }

        return new TBBFKEMCiphertextParameter(c, encapsulatedKeys);
    }

}
