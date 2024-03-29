
package posTagger;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "POSTagger", targetNamespace = "http://postagger.ir.cs/", wsdlLocation = "http://fws.cs.ui.ac.id/RESTFulWSStanfordPOSTagger/POSTagger?wsdl")
public class POSTagger_Service
    extends Service
{

    private final static URL POSTAGGER_WSDL_LOCATION;
    private final static WebServiceException POSTAGGER_EXCEPTION;
    private final static QName POSTAGGER_QNAME = new QName("http://postagger.ir.cs/", "POSTagger");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://fws.cs.ui.ac.id/RESTFulWSStanfordPOSTagger/POSTagger?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        POSTAGGER_WSDL_LOCATION = url;
        POSTAGGER_EXCEPTION = e;
    }

    public POSTagger_Service() {
        super(__getWsdlLocation(), POSTAGGER_QNAME);
    }

    public POSTagger_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), POSTAGGER_QNAME, features);
    }

    public POSTagger_Service(URL wsdlLocation) {
        super(wsdlLocation, POSTAGGER_QNAME);
    }

    public POSTagger_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, POSTAGGER_QNAME, features);
    }

    public POSTagger_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public POSTagger_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns POSTagger
     */
    @WebEndpoint(name = "POSTaggerPort")
    public POSTagger getPOSTaggerPort() {
        return super.getPort(new QName("http://postagger.ir.cs/", "POSTaggerPort"), POSTagger.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns POSTagger
     */
    @WebEndpoint(name = "POSTaggerPort")
    public POSTagger getPOSTaggerPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://postagger.ir.cs/", "POSTaggerPort"), POSTagger.class, features);
    }

    private static URL __getWsdlLocation() {
        if (POSTAGGER_EXCEPTION!= null) {
            throw POSTAGGER_EXCEPTION;
        }
        return POSTAGGER_WSDL_LOCATION;
    }

}
