/**
 * ExecutionWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.borland.sctm.ws.execution;

public interface ExecutionWebService extends java.rmi.Remote {
    public com.borland.sctm.ws.execution.entities.NodeParameter[] getParameters(long sessionId, int execNodeId) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionNode getNode(long sessionId, int nodeId) throws java.rmi.RemoteException;
    public void setParameter(long sessionId, int execNodeId, java.lang.String parameterName, java.lang.String parameterValue) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionNode[] getChildNodes(long sessionId, int parentNodeId) throws java.rmi.RemoteException;
    public int addNode(long sessionId, com.borland.sctm.ws.execution.entities.ExecutionNode node, int parentId) throws java.rmi.RemoteException;
    public java.lang.String[] getPropertyIds(long sessionId, int kind) throws java.rmi.RemoteException;
    public void setCurrentProject(long sessionId, int projectId) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionNode getExecutionRootNode(long sessionId, int projectId) throws java.rmi.RemoteException;
    public int getStateOfExecution(long sessionId, com.borland.sctm.ws.execution.entities.ExecutionHandle handle) throws java.rmi.RemoteException;
    public void updateTestRunStatus(long sessionId, int testRunId, int newStatus, java.lang.String newStatusReason, java.lang.String statusChangeComment) throws java.rmi.RemoteException;
    public int getCurrentProject(long sessionId) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.PropertyMetaInfo getPropertyInfo(long sessionId, int kind, java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String[] getKeywords(long sessionId, int execNodeId) throws java.rmi.RemoteException;
    public void setKeywords(long sessionId, int execNodeId, java.lang.String[] keywords) throws java.rmi.RemoteException;
    public void setSetupTestDefinition(long sessionId, int execNodeId, int testDefId) throws java.rmi.RemoteException;
    public void setCleanupTestDefinition(long sessionId, int execNodeId, int testDefId) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionNode[] getNodes(long sessionId, java.lang.String name) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ManualTester[] getManualTesters(long sessionId, int projectId) throws java.rmi.RemoteException;
    public void deleteNode(long sessionId, int nodeId) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ManualTester[] getAssignedManualTesters(long sessionId, int execDefId) throws java.rmi.RemoteException;
    public int getSetupTestDefinition(long sessionId, int execNodeId) throws java.rmi.RemoteException;
    public int getCleanupTestDefinition(long sessionId, int execNodeId) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionResult getExecutionResult(long sessionId, com.borland.sctm.ws.execution.entities.ExecutionHandle handle) throws java.rmi.RemoteException;
    public void setAssignedManualTesters(long sessionId, int execDefId, int[] manualTesterIds) throws java.rmi.RemoteException;
    public void updateNode(long sessionId, com.borland.sctm.ws.execution.entities.ExecutionNode node) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionHandle[] startExecution(long sessionId, int nodeId, java.lang.String build, int option, java.lang.String sinceBuild) throws java.rmi.RemoteException;
    public com.borland.sctm.ws.execution.entities.ExecutionHandle[] startExecution(long sessionId, int nodeId) throws java.rmi.RemoteException;
}
