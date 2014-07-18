package org.nustaq.reallive;

import org.nustaq.reallive.sys.metadata.Metadata;
import org.nustaq.serialization.FSTConfiguration;

/**
 * Created by ruedi on 21.06.14.
 */
public abstract class RealLive {

    protected FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
    protected String dataDirectory = "/tmp/reallive";

    public FSTConfiguration getConf() {
        return conf;
    }

//    public byte[] toByte( Object o ) {
//        return conf.asByteArray((java.io.Serializable) o);
//    }
//
//    public Object fromByte( byte b[] ) {
//        return conf.asObject(b);
//    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public abstract RLTable getTable(String tableId);
    public abstract RLStream stream(String tableId);
    public abstract Metadata getMetadata();

    public abstract void createTable(String mkt, Class<? extends Record> recordClass);
}