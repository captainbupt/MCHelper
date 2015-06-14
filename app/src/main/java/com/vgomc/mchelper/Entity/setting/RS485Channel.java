package com.vgomc.mchelper.Entity.setting;

import java.util.ArrayList;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class RS485Channel extends Channel {

    public final static int TYPE_MODE_MASTER = 0;
    public final static int TYPE_MODE_SLAVE = 1;
    public final static int TYPE_PROTOCOL_RTU = 0;
    public final static int TYPE_PROTOCOL_ASCII = 1;

    public int mode;
    public int protocol;
    public int slaveAddress;
    public int baudRate;

    public RS485Channel() {
        super(TYPE_RS485, SUBJECT_RS485, Battery.SUBJECT_SWV6, TYPE_SIGNAL_NORMAL);
    }

    public void setProtocol(String protocol) {
        String protocols[] = protocol.split("-");
        if (protocols[0].equals("ACSII")) {
            this.protocol = TYPE_PROTOCOL_ASCII;
        } else {
            this.protocol = TYPE_PROTOCOL_RTU;
        }
        if (protocols[1].equals("M")) {
            this.mode = TYPE_MODE_MASTER;
        } else {
            this.mode = TYPE_MODE_SLAVE;
        }

    }
}
