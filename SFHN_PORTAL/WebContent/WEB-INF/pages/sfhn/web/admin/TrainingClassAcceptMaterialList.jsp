<%@page import="com.lpmas.sfhn.config.TrainingOrganizationConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.TrainingOrganizationInfoHelper"  %>
<%
	int centralizedMaterialCount = (Integer)request.getAttribute("CentralizedMaterialCount");
	int fieldMaterialCount = (Integer)request.getAttribute("FieldMaterialCount");
	String lackOfMaterial = (String)request.getAttribute("LackOfMaterial");
	int classId = (Integer)request.getAttribute("ClassId");
%>

                             <div class="form-group">
                             <div class="col-xs-2 form-label">集中培训材料</div>
                                <div class="col-xs-10 normal">

                                    <span>已上传了<%=centralizedMaterialCount %>/17</span><a class="ml-10" onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classId%>'">查看详情</a>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">田间实训材料</div>
                                <div class="col-xs-10 normal">
                                    <span>已上传了<%=fieldMaterialCount %>/2</span><a class="ml-10" onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=classId%>'">查看详情</a>
                                </div>
                            </div>
                        <p class="bg-info pd-15"> 欠缺材料：<%=lackOfMaterial %></p>