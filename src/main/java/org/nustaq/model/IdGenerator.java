package org.nustaq.model;

/**
 * Created by ruedi on 21.06.14.
 */
public interface IdGenerator<K> {
    K nextid();
}