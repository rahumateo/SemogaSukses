// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;


public class ServiceNotActiveException_Exception extends dictionary.LangridException_Exception {
    private java.lang.String serviceId;
    
    
    public ServiceNotActiveException_Exception(java.lang.String description, java.lang.String serviceId) {
        super(description);
        this.serviceId = serviceId;
    }
    
    public java.lang.String getServiceId() {
        return serviceId;
    }
}
