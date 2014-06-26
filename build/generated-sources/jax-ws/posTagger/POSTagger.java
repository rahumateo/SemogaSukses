
package posTagger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "POSTagger", targetNamespace = "http://postagger.ir.cs/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface POSTagger {


    /**
     * 
     * @param sentence
     * @return
     *     returns java.lang.String
     * @throws IOException_Exception
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPOSTag", targetNamespace = "http://postagger.ir.cs/", className = "posTagger.GetPOSTag")
    @ResponseWrapper(localName = "getPOSTagResponse", targetNamespace = "http://postagger.ir.cs/", className = "posTagger.GetPOSTagResponse")
    @Action(input = "http://postagger.ir.cs/POSTagger/getPOSTagRequest", output = "http://postagger.ir.cs/POSTagger/getPOSTagResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://postagger.ir.cs/POSTagger/getPOSTag/Fault/IOException"),
        @FaultAction(className = Exception_Exception.class, value = "http://postagger.ir.cs/POSTagger/getPOSTag/Fault/Exception")
    })
    public String getPOSTag(
        @WebParam(name = "sentence", targetNamespace = "")
        String sentence)
        throws Exception_Exception, IOException_Exception
    ;

}
