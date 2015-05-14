<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <li id="cate'+data.returnCode+'">
      <div>
        <a class="btn">
          <span class="glyphicon glyphicon-minus"></span>
        </a>
        <a href="/note/list/more?cid='+data.returnCode+'">
          <span id="cName'+data.returnCode+'">'+cName+'</span>
          <span class="badge">0</span>
        </a>
        <div class="dropdown">
          <button class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
          </button>
          <ul class="dropdown-menu dropdown-menu-right" role="menu">
            <li><a href="#" id="del-'+data.returnCode+'">删除</a></li>
            <li><a href="#" id="ren-'+data.returnCode+'">重命名</a></li> 
            <li><a href="#" id="cre-'+data.returnCode+'">新建子文件夹</a></li>
          </ul>
        </div>
      </div>
      <ul class="collapse">
      </ul>
    </li>
