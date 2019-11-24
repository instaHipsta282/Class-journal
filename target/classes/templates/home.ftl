<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="row">
        <div class="card pr-sm-1" style="width: 18rem;">
            <div class="card-body">
                <ul>
                    <li><a>Pass courses: ${passCourses?size}</a></li>
                    <li><a>Courses in progress: ${presentCourses?size}</a></li>
                    <li><a>Future courses: ${futureCourses?size}</a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-7 pl-sm-7">
            <div class="card pr-sm-1" style="width: 30rem;">
                <div class="card-body">
                    <#list actuallyCoursesPercent as course, percent>
                        <h4 class="por-title">${course.getTitle()}</h4>
                        <div class="por-txt">${course.studentsCount} Users (${percent}%)</div>
                        <div class="progress mb-2">
                            <div class="progress-bar bg-flat-color-4" role="progressbar" style="width: ${percent}%;"
                                 aria-valuenow="100" aria-valuemin="100" aria-valuemax="100"></div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>




<#--    <div class="col-xl-8">-->
<#--        <div class="card">-->
<#--            <div class="card-body">-->
<#--                <h4 class="box-title">Orders </h4>-->
<#--            </div>-->
<#--            <div class="card-body--">-->
<#--                <div class="table-stats order-table ov-h">-->
<#--                    <table class="table ">-->
<#--                        <thead>-->
<#--                        <tr>-->
<#--                            <th class="serial">#</th>-->
<#--                            <th class="avatar">Avatar</th>-->
<#--                            <th>ID</th>-->
<#--                            <th>Name</th>-->
<#--                            <th>Product</th>-->
<#--                            <th>Quantity</th>-->
<#--                            <th>Status</th>-->
<#--                        </tr>-->
<#--                        </thead>-->
<#--                        <tbody>-->
<#--                        <tr>-->
<#--                            <td class="serial">1.</td>-->
<#--                            <td class="avatar">-->
<#--                                <div class="round-img">-->
<#--                                    <a href="#"><img class="rounded-circle" src="images/avatar/1.jpg" alt=""></a>-->
<#--                                </div>-->
<#--                            </td>-->
<#--                            <td> #5469 </td>-->
<#--                            <td>  <span class="name">Louis Stanley</span> </td>-->
<#--                            <td> <span class="product">iMax</span> </td>-->
<#--                            <td><span class="count">231</span></td>-->
<#--                            <td>-->
<#--                                <span class="badge badge-complete">Complete</span>-->
<#--                            </td>-->
<#--                        </tr>-->
<#--                        <tr>-->
<#--                            <td class="serial">2.</td>-->
<#--                            <td class="avatar">-->
<#--                                <div class="round-img">-->
<#--                                    <a href="#"><img class="rounded-circle" src="images/avatar/2.jpg" alt=""></a>-->
<#--                                </div>-->
<#--                            </td>-->
<#--                            <td> #5468 </td>-->
<#--                            <td>  <span class="name">Gregory Dixon</span> </td>-->
<#--                            <td> <span class="product">iPad</span> </td>-->
<#--                            <td><span class="count">250</span></td>-->
<#--                            <td>-->
<#--                                <span class="badge badge-complete">Complete</span>-->
<#--                            </td>-->
<#--                        </tr>-->
<#--                        <tr>-->
<#--                            <td class="serial">3.</td>-->
<#--                            <td class="avatar">-->
<#--                                <div class="round-img">-->
<#--                                    <a href="#"><img class="rounded-circle" src="images/avatar/3.jpg" alt=""></a>-->
<#--                                </div>-->
<#--                            </td>-->
<#--                            <td> #5467 </td>-->
<#--                            <td>  <span class="name">Catherine Dixon</span> </td>-->
<#--                            <td> <span class="product">SSD</span> </td>-->
<#--                            <td><span class="count">250</span></td>-->
<#--                            <td>-->
<#--                                <span class="badge badge-complete">Complete</span>-->
<#--                            </td>-->
<#--                        </tr>-->
<#--                        <tr>-->
<#--                            <td class="serial">4.</td>-->
<#--                            <td class="avatar">-->
<#--                                <div class="round-img">-->
<#--                                    <a href="#"><img class="rounded-circle" src="images/avatar/4.jpg" alt=""></a>-->
<#--                                </div>-->
<#--                            </td>-->
<#--                            <td> #5466 </td>-->
<#--                            <td>  <span class="name">Mary Silva</span> </td>-->
<#--                            <td> <span class="product">Magic Mouse</span> </td>-->
<#--                            <td><span class="count">250</span></td>-->
<#--                            <td>-->
<#--                                <span class="badge badge-pending">Pending</span>-->
<#--                            </td>-->
<#--                        </tr>-->
<#--                        <tr class=" pb-0">-->
<#--                            <td class="serial">5.</td>-->
<#--                            <td class="avatar pb-0">-->
<#--                                <div class="round-img">-->
<#--                                    <a href="#"><img class="rounded-circle" src="images/avatar/6.jpg" alt=""></a>-->
<#--                                </div>-->
<#--                            </td>-->
<#--                            <td> #5465 </td>-->
<#--                            <td>  <span class="name">Johnny Stephens</span> </td>-->
<#--                            <td> <span class="product">Monitor</span> </td>-->
<#--                            <td><span class="count">250</span></td>-->
<#--                            <td>-->
<#--                                <span class="badge badge-complete">Complete</span>-->
<#--                            </td>-->
<#--                        </tr>-->
<#--                        </tbody>-->
<#--                    </table>-->
<#--                </div> <!-- /.table-stats &ndash;&gt;-->
<#--            </div>-->
<#--        </div> <!-- /.card &ndash;&gt;-->
<#--    </div>  <!-- /.col-lg-8 &ndash;&gt;-->
</@c.page>