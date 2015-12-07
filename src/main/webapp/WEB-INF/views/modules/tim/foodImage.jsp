<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>美食图片</title>
	<meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/fileupload.jsp" %>
    <script type="text/javascript">
        $(function () {
            'use strict';

            $('#fileupload').fileupload();

            $('#fileupload').fileupload('option', {
                url: ctx + '/tim/food/uploadData',
                disableImageResize: /Android(?!.*Chrome)|Opera/
                        .test(window.navigator.userAgent),
                acceptFileTypes: /(\.|\/)(jpe?g|png)$/i
            });
            $('#fileupload').bind('fileuploadsubmit', function (e, data) {
                var inputs = data.context.find(':input');
                if (inputs.filter(function () {
                            return !this.value && $(this).prop('required');
                        }).first().focus().length) {
                    data.context.find('button').prop('disabled', false);
                    return false;
                }
                data.formData = inputs.serializeArray();
            });
            // Load existing files:
            $('#fileupload').addClass('fileupload-processing');
            $.ajax({
                url: ctx + '/tim/food/findFoodFiles',
                data: {
                    "id": "${food.id}"
                },
                dataType: 'json',
                context: $('#fileupload')[0]
            }).always(function () {
                $(this).removeClass('fileupload-processing');
            }).done(function (result) {
                $(this).fileupload('option', 'done')
                        .call(this, $.Event('done'), {result: result});
            });

        });
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li><a href="${ctx}/tim/food/list">美食列表</a></li>
        <li><a href="${ctx}/tim/food/form?id=${food.id}">美食<shiro:hasPermission name="tim:food:edit">${not empty food.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tim:food:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/tim/food/image?id=${food.id}">美食图片<shiro:hasPermission name="tim:food:edit">维护</shiro:hasPermission><shiro:lacksPermission name="tim:food:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
    <form id="fileupload" class="form-horizontal" action="${ctx}/tim/food/uploadData" method="POST"
          enctype="multipart/form-data">
        <sys:message content="${message}"/>
        <div class="control-group">
            <shiro:hasPermission name="tim:food:edit">
            <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
            <div class="row fileupload-buttonbar">
                <div class="span7">
                    <!-- The fileinput-button span is used to style the file input field as button -->
            <span class="btn btn-success fileinput-button">
                <i class="glyphicon glyphicon-plus"></i>
                <span>上传文件...</span>
                <input type="file" name="file" multiple>
            </span>
                    <button type="submit" class="btn btn-primary start">
                        <i class="glyphicon glyphicon-upload"></i>
                        <span>全部开始</span>
                    </button>
                    <button type="reset" class="btn btn-warning cancel">
                        <i class="glyphicon glyphicon-ban-circle"></i>
                        <span>全部取消</span>
                    </button>
                    <button type="button" class="btn btn-info update">
                        <i class="glyphicon glyphicon-trash"></i>
                        <span>更新选中</span>
                    </button>
                    <button type="button" class="btn btn-danger delete">
                        <i class="glyphicon glyphicon-trash"></i>
                        <span>删除选中</span>
                    </button>
                    <input type="checkbox" class="toggle">
                    <!-- The global file processing state -->
                    <span class="fileupload-process"></span>
                </div>
                <!-- The global progress state -->
                <div class="span5 fileupload-progress fade">
                    <!-- The global progress bar -->
                    <div class="progress progress-striped active" role="progressbar" aria-valuemin="0"
                         aria-valuemax="100">
                        <div class="progress-bar progress-bar-success" style="width:0;"></div>
                    </div>
                    <!-- The extended global progress state -->
                    <div class="progress-extended">&nbsp;</div>
                </div>
            </div>
            </shiro:hasPermission>
            <!-- The table listing the files available for upload/download -->
            <table role="presentation" class="table table-striped">
                <tbody class="files">
                <th>图片</th><th>名称</th><th>类型</th><th style="width: 80px;">描述</th><th></th>
                <shiro:hasPermission name="tim:food:edit"><th>操作</th></shiro:hasPermission>
                </tbody>
            </table>
        </div>
    </form>
    <!-- The blueimp Gallery widget -->
    <div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
        <div class="slides"></div>
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
    </div>
    <!-- The template to display files available for upload -->
    <script id="template-upload" type="text/x-tmpl">
    {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-upload fade">
            <td>
                <span class="preview"></span>
            </td>
            <td>
                <p class="name">{%=file.name%}</p>
                <strong class="error text-danger"></strong>
            </td>
            <td>
                <input type="hidden" name="food" value="${food.id}" />
                <select name="type" class="input-mini" required>
                    <c:forEach var="fileType" items="${fns:getDictList('food_file_type')}">
                        <option value="${fileType.value}" {%=(file.type == ${fileType.value}) ?
                            'selected' :  (${fileType.value == 2} ? 'selected' :'') %}>${fileType.label}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <textarea name="description" rows="3" class="input-xlarge">{%=file.description%}</textarea>
            </td>
            <td>
                <p class="size">上传中...</p>
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
            </td>
            <td>
                {% if (!i && !o.options.autoUpload) { %}
                    <button class="btn btn-primary start" disabled>
                        <i class="glyphicon glyphicon-upload"></i>
                        <span>开始</span>
                    </button>
                {% } %}
                {% if (!i) { %}
                    <button class="btn btn-warning cancel">
                        <i class="glyphicon glyphicon-ban-circle"></i>
                        <span>取消</span>
                    </button>
                {% } %}
            </td>
        </tr>
    {% } %}
    </script>
    <!-- The template to display files available for download -->
    <script id="template-download" type="text/x-tmpl">
    {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-download fade">
            <td>
                <span class="preview">
                    {% if (file.path) { %}
                        <a href="${ctx}/userfiles/{%=file.path%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img style="height:60px;" src="${ctx}/userfiles/{%=file.path%}"></a>
                    {% } %}
                </span>
            </td>
            <td>
                <p class="name">
                    {% if (file.path) { %}
                        <a href="${ctx}/userfiles/{%=file.path%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.path?'data-gallery':''%}>{%=file.name%}</a>
                    {% } else { %}
                        <span>{%=file.name%}</span>
                    {% } %}
                </p>
                {% if (file.error) { %}
                    <div><span class="label label-danger">错误</span> {%=file.error%}</div>
                {% } %}
            </td>
            <td>
                <input type="hidden" name="food" value="${food.id}" />
                <input type="hidden" name="id" value="{%=file.id%}" />
                <select name="type" class="input-mini">
                    <c:forEach var="fileType" items="${fns:getDictList('food_file_type')}">
                        <option value="${fileType.value}" {%=(file.type == ${fileType.value}) ? 'selected' : (${fileType.value == 2} ? 'selected' :'') %}>${fileType.label}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <textarea name="description" rows="3" class="input-xlarge">{%=file.description%}</textarea>
            </td>
            <td></td>
            <shiro:hasPermission name="tim:food:edit">
            <td>
                {% if (file.id) { %}
                    <button class="btn btn-info update" data-type="POST"
                        data-url="${ctx}/tim/food/updateFile">
                        <i class="glyphicon glyphicon-upload"></i>
                        <span>更新</span>
                    </button>
                    <button class="btn btn-danger delete" data-type="POST"
                        data-url="${ctx}/tim/food/deleteFile"
                        data-data='{"fileId": "{%=file.id %}"}' >
                        <i class="glyphicon glyphicon-trash"></i>
                        <span>删除</span>
                    </button>
                    <input type="checkbox" name="delete" value="1" class="toggle">
                {% } else { %}
                    <button class="btn btn-warning cancel">
                        <i class="glyphicon glyphicon-ban-circle"></i>
                        <span>取消</span>
                    </button>
                {% } %}
            </td>
            </shiro:hasPermission>
        </tr>
    {% } %}
    </script>
</body>
</html>