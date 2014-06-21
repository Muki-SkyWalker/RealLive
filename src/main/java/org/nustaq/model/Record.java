package org.nustaq.model;

import org.nustaq.serialization.FSTClazzInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ruedi on 01.06.14.
 */
public class Record implements Serializable {

    String id;
    transient Record originalRecord;
    transient Schema schema;

    public Record(Record originalRecord) {
        this.originalRecord = originalRecord;
        this.id = originalRecord.getId();
        this.schema = originalRecord.schema;
    }

    public Record(String id, Schema schema) {
        this.id = id;
        this.schema = schema;
    }

    public void _setId(String id) {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public RecordChange computeDiff() {
        FSTClazzInfo classInfo = getClassInfo();
        FSTClazzInfo.FSTFieldInfo[] fieldInfo = classInfo.getFieldInfo();

        RecordChange change = new RecordChange(getId());

        ArrayList<FSTClazzInfo.FSTFieldInfo> changedFields = new ArrayList<>();
        ArrayList changedValues = new ArrayList();

        for (int i = 0; i < fieldInfo.length; i++) {
            FSTClazzInfo.FSTFieldInfo fi = fieldInfo[i];
            boolean changed = false;
            try {
                if ( fi.isPrimitive() ) {
                    switch (fi.getIntegralCode(fi.getType())) {
                        case FSTClazzInfo.FSTFieldInfo.BOOL:
                            changed = fi.getBooleanValue(originalRecord) != fi.getBooleanValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.BYTE:
                            changed = fi.getByteValue(originalRecord) != fi.getByteValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.CHAR:
                            changed = fi.getCharValue(originalRecord) != fi.getCharValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.SHORT:
                            changed = fi.getShortValue(originalRecord) != fi.getShortValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.INT:
                            changed = fi.getIntValue(originalRecord) != fi.getIntValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.LONG:
                            changed = fi.getLongValue(originalRecord) != fi.getLongValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.FLOAT:
                            changed = fi.getFloatValue(originalRecord) != fi.getFloatValue(this);
                            break;
                        case FSTClazzInfo.FSTFieldInfo.DOUBLE:
                            changed = fi.getDoubleValue(originalRecord) != fi.getDoubleValue(this);
                            break;
                    }
                } else {
                    changed = fi.getObjectValue(originalRecord) != fi.getObjectValue(this);
                }
                if ( changed ) {
                    changedFields.add(fi);
                    changedValues.add(fi.getField().get(this));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        change.setChanges(changedFields,changedValues);
        return change;
    }

    public FSTClazzInfo getClassInfo() {
        return schema.getConf().getClassInfo(getClass());
    }

    public Object getField( int indexId ) {
        FSTClazzInfo.FSTFieldInfo fi = getClassInfo().getFieldInfo()[indexId];
        try {
            return fi.getField().get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setField( int indexId, Object value ) {
        FSTClazzInfo.FSTFieldInfo fi = getClassInfo().getFieldInfo()[indexId];
        try {
            fi.getField().set(this, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
