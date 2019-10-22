<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>

    <table class="table">
        <thead class="badge-primary badge-lg badge-block">
        <tr>
            <th scope="col">Course ID</th>
            <th scope="col">Title</th>
            <th scope="col">Begin date</th>
            <th scope="col">End date</th>
            <th scope="col">Days count</th>
            <th scope="col">Students count</th>
            <#if isAdmin>
                <th scope="col"></th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#list courses as course>
            <tr>
                <td>${course.id}</td>
                <td><a href="/courseList/${course.id}">${course.title}</a></td>
                <td>${course.startDate}</td>
                <td>${course.endDate}</td>
                <td>${course.daysCount}</td>
                <td>${course.studentsCount}</td>
                <#if isAdmin>
                    <td>
                        <form method="get" action="/courseList/deleteCourse" class="form-inline">
                            <input type="hidden" name="courseId" value="${course.getId()}">
                            <button class="badge badge-danger" type="submit">&times;</button>
                        </form>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
    <#if isAdmin>
        <p>
            <button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="collapse"
                    data-target="#addNewCourse" aria-expanded="false" aria-controls="addNewCourse">Add new course
            </button>
        </p>
        <div class="collapse <#if courseTitleError??>show</#if>" id="addNewCourse">
            <div class="card card-body">
                <div class="collapse <#if courseTitleError??>show</#if>" id="addNewCourse">
                    <div class="form-group mt-3">
                        <form method="post" action="/courseList/addNewCourse" <#-- link -->
                              enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="courseTitle" class="col-sm-5 col-form-label">Course title:</label>
                                <div class="col-sm-7">
                                    <input type="text" id="courseTitle" name="courseTitle"
                                           class="form-control ${(courseTitleError??)?string('is-invalid', '')}"
                                           placeholder="Enter course title"/>
                                    <#if courseTitleError??>
                                        <div class="invalid-feedback">
                                            ${courseTitleError}
                                        </div>
                                    </#if>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="courseDescription" class="col-sm-5 col-form-label">Course description:</label>
                                <div class="col-sm-7">
                                    <textarea class="form-control" id="courseDescription" name="courseDescription"
                                              rows="3"></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="startDate" class="col-sm-5 col-form-label">Start date:</label>
                                <div class="col-sm-7">
                                    <input class="form-control" type="date" value="2019-10-11" id="startDate"
                                           name="startDate">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="endDate" class="col-sm-5 col-form-label">End date:</label>
                                <div class="col-sm-7">
                                    <input class="form-control" type="date" value="2019-10-11" id="endDate"
                                           name="endDate">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="custom-file">
                                    <input type="file" name="courseImage" id="courseImage">
                                    <label class="custom-file-label" for="courseImage">Choose your image</label>
                                </div>
                            </div>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary" type="submit">Add</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </#if>

</@c.page>