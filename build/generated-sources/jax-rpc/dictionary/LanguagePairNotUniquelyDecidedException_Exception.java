// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;


public class LanguagePairNotUniquelyDecidedException_Exception extends dictionary.InvalidParameterException_Exception {
    private java.lang.String[] candidates;
    private java.lang.String message;
    private java.lang.String parameterName1;
    private java.lang.String parameterName2;
    
    
    public LanguagePairNotUniquelyDecidedException_Exception(java.lang.String description, java.lang.String parameterName, java.lang.String[] candidates, java.lang.String message, java.lang.String parameterName1, java.lang.String parameterName2) {
        super(description, parameterName);
        this.candidates = candidates;
        this.message = message;
        this.parameterName1 = parameterName1;
        this.parameterName2 = parameterName2;
    }
    
    public java.lang.String[] getCandidates() {
        return candidates;
    }
    
    public java.lang.String getMessage() {
        return message;
    }
    
    public java.lang.String getParameterName1() {
        return parameterName1;
    }
    
    public java.lang.String getParameterName2() {
        return parameterName2;
    }
}
