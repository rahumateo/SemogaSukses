// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package dictionary;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class UnsupportedMatchingMethodException_Exception_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_parameterName_QNAME = new QName("", "parameterName");
    private static final javax.xml.namespace.QName ns1_matchingMethod_QNAME = new QName("", "matchingMethod");
    private static final javax.xml.namespace.QName ns1_validMethods_QNAME = new QName("", "validMethods");
    private static final javax.xml.namespace.QName ns3_ArrayOf_xsd_string_TYPE_QNAME = new QName("http://fws.cs.ui.ac.id/langrid-service-1.2/services/EnId_bilingualdictionary", "ArrayOf_xsd_string");
    private CombinedSerializer ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1;
    private static final int myDESCRIPTION_INDEX = 0;
    private static final int myPARAMETERNAME_INDEX = 1;
    private static final int myMATCHINGMETHOD_INDEX = 2;
    private static final int myVALIDMETHODS_INDEX = 3;
    
    public UnsupportedMatchingMethodException_Exception_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns2_string_TYPE_QNAME);
        ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1 = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String[].class, ns3_ArrayOf_xsd_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        dictionary.UnsupportedMatchingMethodException_Exception instance = null;
        java.lang.Object descriptionTemp = null;
        java.lang.Object parameterNameTemp = null;
        java.lang.Object matchingMethodTemp = null;
        java.lang.Object validMethodsTemp = null;
        dictionary.UnsupportedMatchingMethodException_Exception_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_description_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_description_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.UnsupportedMatchingMethodException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDESCRIPTION_INDEX, builder);
                    isComplete = false;
                } else {
                    descriptionTemp = member;
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_parameterName_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_parameterName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.UnsupportedMatchingMethodException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPARAMETERNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    parameterNameTemp = member;
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_matchingMethod_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_matchingMethod_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.UnsupportedMatchingMethodException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMATCHINGMETHOD_INDEX, builder);
                    isComplete = false;
                } else {
                    matchingMethodTemp = member;
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_validMethods_QNAME)) {
                member = ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1.deserialize(ns1_validMethods_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.UnsupportedMatchingMethodException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myVALIDMETHODS_INDEX, builder);
                    isComplete = false;
                } else {
                    validMethodsTemp = member;
                }
                reader.nextElementContent();
            }
        }
        if (isComplete) {
            instance = new dictionary.UnsupportedMatchingMethodException_Exception((java.lang.String)descriptionTemp, (java.lang.String)parameterNameTemp, (java.lang.String)matchingMethodTemp, (java.lang.String[])validMethodsTemp);
        } else {
            builder.setDescription((java.lang.String)descriptionTemp);
            builder.setParameterName((java.lang.String)parameterNameTemp);
            builder.setMatchingMethod((java.lang.String)matchingMethodTemp);
            builder.setValidMethods((java.lang.String[])validMethodsTemp);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        dictionary.UnsupportedMatchingMethodException_Exception instance = (dictionary.UnsupportedMatchingMethodException_Exception)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        dictionary.UnsupportedMatchingMethodException_Exception instance = (dictionary.UnsupportedMatchingMethodException_Exception)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getParameterName(), ns1_parameterName_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getMatchingMethod(), ns1_matchingMethod_QNAME, null, writer, context);
        ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1.serialize(instance.getValidMethods(), ns1_validMethods_QNAME, null, writer, context);
    }
}