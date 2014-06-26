// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ServerBusyException_Exception_SOAPBuilder implements SOAPInstanceBuilder {
    private dictionary.ServerBusyException_Exception _instance;
    private java.lang.String description;
    private static final int myDESCRIPTION_INDEX = 0;
    
    public ServerBusyException_Exception_SOAPBuilder() {
    }
    
    
    public ServerBusyException_Exception_SOAPBuilder(java.lang.String description) {
        this.description = description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDESCRIPTION_INDEX:
                return GATES_CONSTRUCTION | REQUIRES_INITIALIZATION;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public void construct() {
        _instance = new dictionary.ServerBusyException_Exception(description);
    }
    
    public void setMember(int index, java.lang.Object memberValue) {
        try {
            switch(index) {
                case myDESCRIPTION_INDEX:
                    setDescription((java.lang.String)memberValue);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }
        catch (java.lang.RuntimeException e) {
            throw e;
        }
        catch (java.lang.Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }
    
    public void initialize() {
    }
    
    public void setInstance(java.lang.Object instance) {
        _instance = (dictionary.ServerBusyException_Exception)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
