package cn.edu.buaa.crypto.encryption.tbbfkem.generators;

import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import cn.edu.buaa.crypto.encryption.fspibme.serparams.FSPIBMECiphertextSerParameter;
import cn.edu.buaa.crypto.encryption.fspibme.serparams.FSPIBMEKeySerParameter;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.HIBEBBG05Engine;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.serparams.HIBEBBG05PublicKeySerParameter;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.serparams.HIBEBBG05SecretKeySerParameter;
import cn.edu.buaa.crypto.encryption.tbbfkem.serparams.TBBFKEMCiphertextParameter;
import cn.edu.buaa.crypto.encryption.tbbfkem.serparams.TBBFKEMSecretKeyParameter;
import cn.edu.buaa.crypto.utils.PairingUtils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class TBBFKEMDecryptionGenerator {
    private HIBEBBG05PublicKeySerParameter publicKeyParameter;
    private TBBFKEMCiphertextParameter C;
    private TBBFKEMSecretKeyParameter sk;
    private HIBEBBG05Engine hibebbg05Engine;

    public void init(HIBEBBG05Engine hibebbg05Engine, PairingKeySerParameter publicKey, TBBFKEMSecretKeyParameter sk,
                     TBBFKEMCiphertextParameter C) {
        this.hibebbg05Engine = hibebbg05Engine;
        this.publicKeyParameter = (HIBEBBG05PublicKeySerParameter) publicKey;
        this.sk = sk;
        this.C = C;
    }

    public Element computeDecapsulation() throws InvalidCipherTextException {
        Pairing pairing = PairingFactory.getPairing(publicKeyParameter.getParameters());

        HIBEBBG05SecretKeySerParameter secretKeyParameters = (HIBEBBG05SecretKeySerParameter) dk.getTk().get("E000");
        Element sig = ciphertext.getSig();
        Element x = pairing.getZr().newElement();
        x.set(10086);
        System.out.println(PairingUtils.SigVerify(publicKeyParameter, ciphertext.getSig_pk(), sig, x));
        this.message = hibebbg05Engine.decryption(publicKeyParameter, secretKeyParameters, tau, W);
        return this.message;
    }
}
