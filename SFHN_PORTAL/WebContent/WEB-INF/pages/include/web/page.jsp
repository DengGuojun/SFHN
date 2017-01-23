<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form name="formPage" method="post" action="">
		<input type="hidden" name="pageNum" id="pageNum" value=""/>
		<%for(String[] condArray :COND_LIST){ %>
		<input type="hidden" name="<%=condArray[0] %>" id="<%=condArray[0] %>" value="<%=condArray[1] %>"/><%} %>
</form>
         <div class="text-center">
                        <ul class="pagination">
                        <%if(PAGE_BEAN.getCurrentPageNumber()>1){ %>
                            <li>
                              <a aria-label="Previous" onclick="javascript:goPage('formPage',<%=PAGE_BEAN.getPrePageNumber() %>);">
                                <span aria-hidden="true">&laquo;</span>
                              </a>
                            </li>
                         <%} %>                  
                          <%if(PAGE_BEAN.getTotalPageNumber() >1){ %>
				          <%for(int i=PAGE_BEAN.getCurrentPageNumber(); i<=PAGE_BEAN.getTotalPageNumber() ; i++) {%>
				          <%if(i-PAGE_BEAN.getCurrentPageNumber() < 5) {%>
				          <li class="<%=PAGE_BEAN.getCurrentPageNumber() == i ? "active" : "" %>"><a onclick="javascript:goPage('formPage',<%=i %>);"><%=i%></a></li>
				          <%} %>
				          <%} %>
		                 <%} %>
                         <%if(PAGE_BEAN.getCurrentPageNumber() < PAGE_BEAN.getTotalPageNumber()){ %>
                            <li>
                              <a aria-label="Next" onclick="javascript:goPage('formPage',<%=PAGE_BEAN.getNextPageNumber() %>);">
                                <span aria-hidden="true">&raquo;</span>
                              </a>
                            </li>
                          <%} %>
                       </ul>
         </div>