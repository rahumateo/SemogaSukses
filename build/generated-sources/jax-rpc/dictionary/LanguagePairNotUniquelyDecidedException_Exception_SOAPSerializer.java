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

public class LanguagePairNotUniquelyDecidedException_Exception_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_parameterName_QNAME = new QName("", "parameterName");
    private static final javax.xml.namespace.QName ns1_candidates_QNAME = new QName("", "candidates");
    private static final javax.xml.namespace.QName ns3_ArrayOf_xsd_string_TYPE_QNAME = new QName("http://fws.cs.ui.ac.id/langrid-service-1.2/services/EnId_bilingualdictionary", "ArrayOf_xsd_string");
    private CombinedSerializer ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1;
    private static final javax.xml.namespace.QName ns1_message_QNAME = new QName("", "message");
    private static final javax.xml.namespace.QName ns1_parameterName1_QNAME = new QName("", "parameterName1");
    private static final javax.xml.namespace.QName ns1_parameterName2_QNAME = new QName("", "parameterName2");
    private static final int myDESCRIPTION_INDEX = 0;
    private static final int myPARAMETERNAME_INDEX = 1;
    private static final int myCANDIDATES_INDEX = 2;
    private static final int myMESSAGE_INDEX = 3;
    private static final int myPARAMETERNAME1_INDEX = 4;
    private static final int myPARAMETERNAME2_INDEX = 5;
    
    public LanguagePairNotUniquelyDecidedException_Exception_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns2_string_TYPE_QNAME);
        ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1 = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String[].class, ns3_ArrayOf_xsd_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        dictionary.LanguagePairNotUniquelyDecidedException_Exception instance = null;
        java.lang.Object descriptionTemp = null;
        java.lang.Object parameterNameTemp = null;
        java.lang.Object candidatesTemp = null;
        java.lang.Object messageTemp = null;
        java.lang.Object parameterName1Temp = null;
        java.lang.Object parameterName2Temp = null;
        dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder builder = null;
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
                        builder = new dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder();
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
                        builder = new dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder();
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
            if (elementName.equals(ns1_candidates_QNAME)) {
                member = ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1.deserialize(ns1_candidates_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCANDIDATES_INDEX, builder);
                    isComplete = false;
                } else {
                    candidatesTemp = member;
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_message_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_message_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMESSAGE_INDEX, builder);
                    isComplete = false;
                } else {
                    messageTemp = member;
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_parameterName1_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_parameterName1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPARAMETERNAME1_INDEX, builder);
                    isComplete = false;
                } else {
                    parameterName1Temp = member;
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_parameterName2_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_parameterName2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new dictionary.LanguagePairNotUniquelyDecidedException_Exception_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPARAMETERNAME2_INDEX, builder);
                    isComplete = false;
                } else {
                    parameterName2Temp = member;
                }
                reader.nextElementContent();
            }
        }
        if (isComplete) {
            instance = new dictionary.LanguagePairNotUniquelyDecidedException_Exception((java.lang.String)descriptionTemp, (java.lang.String)parameterNameTemp, (java.lang.String[])candidatesTemp, (java.lang.String)messageTemp, (java.lang.String)parameterName1Temp, (java.lang.String)parameterName2Temp);
        } else {
            builder.setDescription((java.lang.String)descriptionTemp);
            builder.setParameterName((java.lang.String)parameterNameTemp);
            builder.setCandidates((java.lang.String[])candidatesTemp);
            builder.setMessage((java.lang.String)messageTemp);
            builder.setParameterName1((java.lang.String)parameterName1Temp);
            builder.setParameterName2((java.lang.String)parameterName2Temp);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        dictionary.LanguagePairNotUniquelyDecidedException_Exception instance = (dictionary.LanguagePairNotUniquelyDecidedException_Exception)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        dictionary.LanguagePairNotUniquelyDecidedException_Exception instance = (dictionary.LanguagePairNotUniquelyDecidedException_Exception)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getParameterName(), ns1_parameterName_QNAME, null, writer, context);
        ns3_myns3_ArrayOf_xsd_string__StringArray_SOAPSerializer1.serialize(instance.getCandidates(), ns1_candidates_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getMessage(), ns1_message_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getParameterName1(), ns1_parameterName1_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getParameterName2(), ns1_parameterName2_QNAME, null, writer, context);
    }
}