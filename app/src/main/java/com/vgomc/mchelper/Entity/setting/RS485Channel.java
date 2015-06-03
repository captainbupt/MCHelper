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
    }

    public RS485Channel(ArrayList<Variable> variables) {
        super(TYPE_RS485, SUBJECT_RS485, variables);
    }

    public RS485Channel(ArrayList<Variable> variables, int mode, int protocol, int slaveAddress, int baudRate) {
        this(variables);
        this.mode = mode;
        this.protocol = protocol;
        this.slaveAddress = slaveAddress;
        this.baudRate = baudRate;
    }
}
