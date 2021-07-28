package com.example.encryption.hibe;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerPair;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import cn.edu.buaa.crypto.encryption.fspibme.FSPIBMEEngine;
import cn.edu.buaa.crypto.encryption.fspibme.serparams.FSPIBMECiphertextSerParameter;
import cn.edu.buaa.crypto.encryption.fspibme.serparams.FSPIBMEKeySerParameter;
import cn.edu.buaa.crypto.encryption.fspibme.utils.BinaryTreeBuild;
import cn.edu.buaa.crypto.encryption.fspibme.utils.TestUtils;
import cn.edu.buaa.crypto.encryption.hibe.bbg05.HIBEBBG05Engine;
import cn.edu.buaa.crypto.encryption.tbbfkem.generators.TBBFKEMKeyGenerator;
import cn.edu.buaa.crypto.encryption.tbbfkem.serparams.TBBFKEMSecretKeyParameter;
import cn.edu.buaa.crypto.utils.FileTransferClient;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.io.*;


public class TimeBaseBFETest {

    @Test
    public void test1() throws Exception {

//
//        StopWatch timerEnc = new StopWatch();
//        StopWatch timerDec = new StopWatch();
//        StopWatch timerPunc = new StopWatch();
//
//        String[] ids = null;
//        int num = 16;
//        for (int i = 0; i <= num; i++) {
//            if (i == 0) {
//                ids = new String[num + 1];
//                ids[0] = "E";
//            } else {
//                ids[i] = "0";
//            }
//        }
//        String[] ids = {"E", "0", "0", "0","0", "0", "0","0", "0", "0","0", "0", "0","0", "0","0","0"};
//        String tau = "0000000000000";
//        String tag = "000";

        PairingParameters pairingParameters = PairingFactory.getPairingParameters(TestUtils.TEST_PAIRING_PARAMETERS_PATH_a_80_256);
        Pairing pairing = PairingFactory.getPairing(pairingParameters);
        HIBEBBG05Engine engine = HIBEBBG05Engine.getInstance();

        //Setup
        PairingKeySerPair keyPair = engine.setup(pairingParameters, BinaryTreeBuild.depth);
        PairingKeySerParameter publicKey = keyPair.getPublic();
        PairingKeySerParameter masterKey = keyPair.getPrivate();
        TBBFKEMKeyGenerator tbbfkemKeyGenerator = new TBBFKEMKeyGenerator();
        tbbfkemKeyGenerator.init(publicKey, masterKey, 5, 0.001, 5);

        TBBFKEMSecretKeyParameter sk = tbbfkemKeyGenerator.generateKey();

        System.out.println("-------------------- TEST END ------------------------");
    }
}
