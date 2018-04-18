package com.manystar.dms.cxf.incident;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.soap.SOAPFactoryImpl;

public class DmsIncidentServicePortTypeProxy implements com.manystar.dms.cxf.incident.DmsIncidentServicePortType {
  private String _endpoint = null;
  private com.manystar.dms.cxf.incident.DmsIncidentServicePortType dmsIncidentServicePortType = null;
  
  public DmsIncidentServicePortTypeProxy() {
    _initDmsIncidentServicePortTypeProxy();
  }
  
  public DmsIncidentServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initDmsIncidentServicePortTypeProxy();
  }
  
  private void _initDmsIncidentServicePortTypeProxy() {
    try {
      dmsIncidentServicePortType = (new com.manystar.dms.cxf.incident.DmsIncidentServiceLocator()).getDmsIncidentServicePort();
      if (dmsIncidentServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dmsIncidentServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dmsIncidentServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dmsIncidentServicePortType != null)
      ((javax.xml.rpc.Stub)dmsIncidentServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.manystar.dms.cxf.incident.DmsIncidentServicePortType getDmsIncidentServicePortType() {
    if (dmsIncidentServicePortType == null)
      _initDmsIncidentServicePortTypeProxy();
    return dmsIncidentServicePortType;
  }
  
  public java.lang.String dmsProductAnalysis(java.lang.String arg0) throws java.rmi.RemoteException{
    if (dmsIncidentServicePortType == null)
      _initDmsIncidentServicePortTypeProxy();
    return dmsIncidentServicePortType.dmsProductAnalysis(arg0);
  }
  
  public java.lang.String dmsOmsOrderCancel(java.lang.String arg0) throws java.rmi.RemoteException{
    if (dmsIncidentServicePortType == null)
      _initDmsIncidentServicePortTypeProxy();
    return dmsIncidentServicePortType.dmsOmsOrderCancel(arg0);
  }
  
  public java.lang.String dmsOmsOrderAnalysis(java.lang.String arg0) throws java.rmi.RemoteException{
    if (dmsIncidentServicePortType == null)
      _initDmsIncidentServicePortTypeProxy();
    return dmsIncidentServicePortType.dmsOmsOrderAnalysis(arg0);
  }
  
  public java.lang.String dmsSupplierAnalysis(java.lang.String arg0) throws java.rmi.RemoteException{
    if (dmsIncidentServicePortType == null)
      _initDmsIncidentServicePortTypeProxy();
    return dmsIncidentServicePortType.dmsSupplierAnalysis(arg0);
  }
  
  
}