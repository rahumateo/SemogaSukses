// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;


public class InvalidParameterException_Exception extends dictionary.LangridException_Exception {
    private java.lang.String parameterName;
    
    
    public InvalidParameterException_Exception(java.lang.String description, java.lang.String parameterName) {
        super(description);
        this.parameterName = parameterName;
    }
    
    public java.lang.String getParameterName() {
        return parameterName;
    }
}
