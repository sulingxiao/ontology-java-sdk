/*
 * Copyright (C) 2018 The ontology Authors
 * This file is part of The ontology library.
 *
 *  The ontology is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The ontology is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with The ontology.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package demo;

import com.github.ontio.core.payload.InvokeCode;
import com.github.ontio.OntSdk;
import com.github.ontio.sdk.wallet.Identity;
import com.github.ontio.sdk.wallet.Wallet;
import com.github.ontio.network.websocket.MsgQueue;
import com.github.ontio.network.websocket.Result;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;


/**
 *
 */

public class OntIdDemoTest {
    public static Object lock = new Object();
    public static void main(String[] args) {
        try {
            OntSdk ontSdk = getOntSdk();

            ontSdk.getWebSocket().startWebsocketThread(false);

            Wallet oep6 = ontSdk.getWalletMgr().getWallet();
            System.out.println("oep6:" + JSON.toJSONString(oep6));
            //System.exit(0);

            System.out.println();
            System.out.println("================register=================");
            //registry ontid
            Identity ident = ontSdk.getOntIdTx().sendRegister("passwordtest");
            Thread.sleep(6000);
            String ontid = ident.ontid;


            System.out.println();
            System.out.println("===============updateAttribute==================");

            String attri = "attri";
            Map recordMap = new HashMap();
            recordMap.put("key0", "world0");
            //recordMap.put("key1", i);
            recordMap.put("keyNum", 1234589);
            recordMap.put("key2", false);


            System.out.println(JSON.toJSONString(recordMap));
            System.out.println(ontid);
            String hash = ontSdk.getOntIdTx().sendUpdateAttribute(ontid, "passwordtest", attri.getBytes(), "Json".getBytes(), JSON.toJSONString(recordMap).getBytes());
            System.out.println("hash:" + hash);


            Thread.sleep(6000);

            System.out.println();
            System.out.println("===============getDDO==================");

            String ddo = ontSdk.getOntIdTx().sendGetDDO(ontid);
            System.out.println("Ddo:" + ddo);


            System.out.println();
            System.out.println("===============get Transaction==================");
            InvokeCode t = (InvokeCode) ontSdk.getConnectMgr().getTransaction(hash);

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OntSdk getOntSdk() throws Exception {
        String ip = "http://127.0.0.1";
//        String ip = "http://54.222.182.88;
//        String ip = "http://101.132.193.149";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
        wm.setWesocket(wsUrl,lock);

        wm.openWalletFile("OntIdDemo.json");

        wm.setCodeAddress("80e7d2fc22c24c466f44c7688569cc6e6d6c6f92");
        return wm;
    }
}
