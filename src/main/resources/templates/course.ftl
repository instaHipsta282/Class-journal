<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div class="row m-4">
        <div class="col-md">
            <h1>${course.title}</h1>
        </div>
    </div>
    <div class="row m-4">
    <div class="col-md-3">
        <div class="card-deck">
            <div class="card">
                <img src="/img/${course.getImage()}" class="card-img-top">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        Students count: ${course.getStudentsCount()}
                    </li>
                    <li class="list-group-item">
                        Days count: ${course.getDaysCount()}
                    </li>
                    <li class="list-group-item">
                        <#if course.getDescription()??>${course.getDescription()}<#else>Description not allowed</#if>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="col-md-9 col-md-offset-4">
        <link rel="stylesheet" href="../static/colorsForTable.css" TYPE="text/css">
        <div class="table-responsive">
            <table id="example" class="table">
                <thead>
                <tr>
                    <th>Student</th>
                    <#list scheduleDays as day>
                        <th>${day}</th>
                    </#list>
                    <#if isAdmin>
                        <th scope="col"></th>
                    </#if>
                </tr>
                </thead>
                <tbody>
                <#list usersSchedule as key, value>
                    <tr>
                        <td>
                            ${key.getLastName()} ${key.getFirstName()}
                        </td>
                        <#list value as schedule>
                            <td
                                    <#if schedule.getPresenceStatus() == "NONE">
                                        id="noneColor"
                                    </#if>
                                    <#if schedule.getPresenceStatus() == "PRESENCE">
                                        id="presenceColor"
                                    </#if>
                                    <#if schedule.getPresenceStatus() = "ABSENT">
                                        id="absentColor"
                                    </#if>
                            >
                                <p>
                                    <#if schedule.getScore() != "NONE">
                                        ${schedule.getScore()}
                                    <#else >
                                        -
                                    </#if>
                                </p>
                            </td>
                        </#list>
                        <#if isAdmin>
                            <td>
                                <form method="get"
                                      action="/courseList/${course.getId()}/deleteUser"
                                      class="form-inline">
                                    <input type="hidden" name="userId" value="${key.getId()}">
                                    <button class="badge badge-danger" type="submit">&times;</button>
                                </form>
                            </td>
                        </#if>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <#if isAdmin>



        <div class="btn-group m-9" role="group" aria-label="Button group with nested dropdown"
             style="width: 100%;">
            <#if newStudentExist>
                <div class="dropdown show mr-5">
                    <a class="btn btn-outline-primary btn-lg btn-block dropdown-toggle" href="#" role="button"
                       id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false" style="width: 130%;">
                        Add user
                    </a>
                    <form action="/courseList/${course.getId()}/addUser" method="post">
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <input class="form-control ${(typeError??)?string('is-invalid', '')}" id="myInput"
                                   type="text" placeholder="Search.." name="userName">
                            <br>
                            <#list usersForCourse as userForCourse>
                                <ul class="list-group" id="myList">
                                    <li class="list-group-item">
                                        <div class="input-group-text mr-2">
                                            <input class="mr-2" type="checkbox"
                                                   name="usersId" value="${userForCourse.getId()}"/>
                                            <#if typeError??>
                                                <div class="invalid-feedback">
                                                    ${typeError}
                                                </div>
                                            </#if>
                                            ${userForCourse.getLastName()} ${userForCourse.getFirstName()}
                                        </div>
                                    </li>
                                </ul>
                            </#list>
                            <input type="hidden" value="${_csrf.token}" name="_csrf">
                            <button type="submit" class="btn btn-primary btn-lg btn-block">
                                Save
                            </button>
                        </div>
                    </form>
                </div>

                <script>
                    $(document).ready(function () {
                        $("#myInput").on("keyup", function () {
                            var value = $(this).val().toLowerCase();
                            $("#myList li").filter(function () {
                                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                            });
                        });
                    });
                </script>
            </#if>


            <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="collapse"
                    data-target="#changeSchedule" aria-expanded="false" aria-controls="addNewCourse"
                    style="height: 47px;">
                Change schedule
            </button>
        </div>
        <div id="changeSchedule" class="collapse">
            <div class="card card-body">
                <div id="changeSchedule">
                    <div class="form-group mt-3">
                        <form method="post" action="/courseList/${course.getId()}/changeSchedule" <#-- link -->
                              enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="user"
                                       class="col-sm-5 col-form-label">Student</label>
                                <div class="col-sm-7">
                                    <select class="form-control" id="user" name="user">
                                        <#list usersSchedule as key, value>
                                            <option value="${key.getId()}">
                                                ${key.getLastName()} ${key.getFirstName()}
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="date" class="col-sm-5 col-form-label">
                                    Date
                                </label>
                                <div class="col-sm-7">
                                    <select class="form-control" id="date" name="date">
                                        <#list scheduleDays as day>
                                            <option value="${day}">${day}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="presenceStatus" class="col-sm-5 col-form-label">
                                    Presence status
                                </label>
                                <div class="col-sm-7">
                                    <select class="form-control" id="presenceStatus" name="presenceStatus">
                                        <#list presenceStatuses as status>
                                            <option value="${status}">${status}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="score" class="col-sm-5 col-form-label">
                                    Score
                                </label>
                                <div class="col-sm-7">
                                    <select class="form-control" id="score" name="score">
                                        <#list scores as score>
                                            <#if score = "NONE">
                                                <option value="${score}">-</option>
                                            <#else>
                                                <option value="${score}">${score}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary" type="submit">Change</button>
                        </form>
                    </div>
                    <#--                    </div>-->
                </div>
            </div>
            </#if>
        </div>
    </div>

</@c.page>