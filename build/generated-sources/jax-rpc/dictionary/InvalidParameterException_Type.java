// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;


public class InvalidParameterException_Type extends dictionary.LangridException_Type {
    protected java.lang.String parameterName;
    
    public InvalidParameterException_Type() {
    }
    
    public InvalidParameterException_Type(java.lang.String description, java.lang.String parameterName) {
        this.description = description;
        this.parameterName = parameterName;
    }
    
    public java.lang.String getParameterName() {
        return parameterName;
    }
    
    public void setParameterName(java.lang.String parameterName) {
        this.parameterName = parameterName;
    }
}
