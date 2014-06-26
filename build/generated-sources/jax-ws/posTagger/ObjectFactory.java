
package posTagger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the posTagger package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetPOSTag_QNAME = new QName("http://postagger.ir.cs/", "getPOSTag");
    private final static QName _GetPOSTagResponse_QNAME = new QName("http://postagger.ir.cs/", "getPOSTagResponse");
    private final static QName _IOException_QNAME = new QName("http://postagger.ir.cs/", "IOException");
    private final static QName _Exception_QNAME = new QName("http://postagger.ir.cs/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: posTagger
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link GetPOSTagResponse }
     * 
     */
    public GetPOSTagResponse createGetPOSTagResponse() {
        return new GetPOSTagResponse();
    }

    /**
     * Create an instance of {@link GetPOSTag }
     * 
     */
    public GetPOSTag createGetPOSTag() {
        return new GetPOSTag();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPOSTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://postagger.ir.cs/", name = "getPOSTag")
    public JAXBElement<GetPOSTag> createGetPOSTag(GetPOSTag value) {
        return new JAXBElement<GetPOSTag>(_GetPOSTag_QNAME, GetPOSTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPOSTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://postagger.ir.cs/", name = "getPOSTagResponse")
    public JAXBElement<GetPOSTagResponse> createGetPOSTagResponse(GetPOSTagResponse value) {
        return new JAXBElement<GetPOSTagResponse>(_GetPOSTagResponse_QNAME, GetPOSTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://postagger.ir.cs/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://postagger.ir.cs/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
