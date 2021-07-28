package cn.edu.buaa.crypto.encryption.tbbfkem.generators;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import cn.edu.buaa.crypto.encryption.BloomFilterEncryption.BloomFilter;
import cn.edu.buaa.crypto.encryption.BloomFilterEncryption.Filter;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.HIBEBBG05Engine;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.generators.HIBEBBG05SecretKeyGenerator;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.serparams.HIBEBBG05MasterSecretKeySerParameter;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.serparams.HIBEBBG05PublicKeySerParameter;
import cn.edu.buaa.crypto.encryption.hibe.genparams.HIBEDelegateGenerationParameter;
import cn.edu.buaa.crypto.encryption.hibe.genparams.HIBESecretKeyGenerationParameter;
import cn.edu.buaa.crypto.encryption.tbbfkem.Utils;
import cn.edu.buaa.crypto.encryption.tbbfkem.serparams.TBBFKEMSecretKeyParameter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TBBFKEMKeyGenerator {
    private HIBEBBG05MasterSecretKeySerParameter masterSecretKeyParameter;
    private HIBEBBG05PublicKeySerParameter publicKeyParameter;
    private int filterElementNumber;
    private double filterFalsePositiveProbability;
    private int timeSlotsExponent;

    public void init(PairingKeySerParameter publicKey, PairingKeySerParameter masterKey, int filterElementNumber,
                     double filterFalsePositiveProbability, int timeSlotsExponent) {
        this.masterSecretKeyParameter = (HIBEBBG05MasterSecretKeySerParameter) masterKey;
        this.publicKeyParameter = (HIBEBBG05PublicKeySerParameter) publicKey;
        this.filterElementNumber = filterElementNumber;
        this.filterFalsePositiveProbability = filterFalsePositiveProbability;
        this.timeSlotsExponent = timeSlotsExponent;
    }

    public TBBFKEMSecretKeyParameter generateKey() {

        HIBEBBG05SecretKeyGenerator secretKeyGenerator = new HIBEBBG05SecretKeyGenerator();
        Map<String, PairingKeySerParameter> sk_bloom = new HashMap<String, PairingKeySerParameter>();
        Map<String, PairingKeySerParameter> sk_time = new HashMap<String, PairingKeySerParameter>();
        secretKeyGenerator.init(new HIBESecretKeyGenerationParameter(publicKeyParameter, masterSecretKeyParameter, new String[]{"e"}));
        sk_time.put("e", secretKeyGenerator.generateKey());

        Filter filter = new BloomFilter(filterElementNumber, filterFalsePositiveProbability);
        int bloomFilterTreeDepth = Utils.calculateTargetBloomFilterTreeDepth(filter.size());

        String startNodeId = Utils.padStringWithZeros("", timeSlotsExponent);
        secretKeyGenerator.init(new HIBEDelegateGenerationParameter(publicKeyParameter, sk_time.get("e"), startNodeId));
        sk_time.put("e"+startNodeId, secretKeyGenerator.generateKey());


        Set<String> strs = Utils.getStrs(bloomFilterTreeDepth);
        String prefix = "e"+startNodeId;
        for (String str : strs) {
            secretKeyGenerator.init(new HIBEDelegateGenerationParameter(publicKeyParameter, sk_time.get(prefix), str));
            PairingKeySerParameter sk_i = secretKeyGenerator.generateKey();
            sk_bloom.put(prefix + str, sk_i);
        }


        return new TBBFKEMSecretKeyParameter(publicKeyParameter.getParameters(), filter, sk_bloom, sk_time);
    }
}
