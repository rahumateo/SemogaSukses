// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;


public class UnsupportedLanguagePairException_Exception extends dictionary.InvalidParameterException_Exception {
    private java.lang.String language1;
    private java.lang.String language2;
    private java.lang.String parameterName1;
    private java.lang.String parameterName2;
    
    
    public UnsupportedLanguagePairException_Exception(java.lang.String description, java.lang.String parameterName, java.lang.String language1, java.lang.String language2, java.lang.String parameterName1, java.lang.String parameterName2) {
        super(description, parameterName);
        this.language1 = language1;
        this.language2 = language2;
        this.parameterName1 = parameterName1;
        this.parameterName2 = parameterName2;
    }
    
    public java.lang.String getLanguage1() {
        return language1;
    }
    
    public java.lang.String getLanguage2() {
        return language2;
    }
    
    public java.lang.String getParameterName1() {
        return parameterName1;
    }
    
    public java.lang.String getParameterName2() {
        return parameterName2;
    }
}
