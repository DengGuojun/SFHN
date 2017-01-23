<div class="col-xs-4 left-side">
                <div class="row">
                    <div class="main-hd">
                        <div class="manage-logo"></div>
                        <%if(isGovernment){ %>
                        <p class="mg-title">主管部门: <sapn><%=userName %></sapn></p>
                        <%}else{ %>
                        <p class="mg-title">培育机构: <sapn><%=userName %></sapn></p>
                        <%} %>
                    </div>
                    <div class="main-bd" id="wrap">
                        <ul class="nav-box" id="cont">
                            <!-- 显示提示 has-tips 选中 selected -->
                            <li class="nav-item "><a href="Index.do">首页</a></li>
                            <%if(unreadMessageCount > 0){ %>
                            <li class="nav-item has-tips"><a href="MessageInfoList.do">待办通知</a><span class="num"><%=unreadMessageCount %></span></li>
                            <%}else{ %>
                            <li class="nav-item has-tips"><a href="MessageInfoList.do">待办通知</a></li>
                            <%} %>
                            <li class="nav-item"><a href="TrainingClassInfoList.do">培训班审批</a></li>
                            <li class="nav-item"><a href="TrainingClassUserBaseList.do">培育对象库</a></li>
                            <li class="nav-item"><a href="TeacherInfoList.do">培育师资库</a></li>
                            <li class="nav-item"><a href="TrainingCourseInfoList.do">培育课程库</a></li>
                            <li class="nav-item"><a href="TrainingClassTargetList.do">数据统计</a></li>
                            <%if(isGovernment) {%>
                            <li class="nav-item"><a href="TrainingClassInfoMonitorList.do">现场监控</a></li>
                            <%} %>
                            <%if(isGovernment && governmentOrgInfoBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE){ %>
                            <li class="nav-item"><a href="AnnouncementInfoList.do">公告通知</a></li>
                            <%} %>
                        </ul>
                    </div>
                </div>
            </div>