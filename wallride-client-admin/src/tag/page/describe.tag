<page-describe xmlns:th="http://www.thymeleaf.org">
    <div id="wr-page-header">
        <div class="page-header container-fluid">
            <div class="row">
                <div class="col-sm-9">
                    <div class="pull-left back">
                        <a class="btn btn-sm btn-default" th:href=""><span class="glyphicon glyphicon-arrow-left"></span></a>
                    </div>
                    <div class="pull-left">
                        <h1 th:text="">Page Title</h1>
                        <p class="small"><a th:href="" th:text="" th:if="" target="_blank"></a></p>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="pull-left">
                        <div class="tools clearfix">
                            <div class="status">
                                <span th:if="" class="glyphicon glyphicon-warning-sign"></span>
                                <span th:if="" class="glyphicon glyphicon-time"></span>
                                <span th:if="" th:text="">Published</span>
                                <a th:href="" th:if="" target="_blank">
                                    <span class="glyphicon glyphicon-globe"></span>
                                    <span th:text="">Published</span>
                                </a>
                            </div>
                            <p class="small"><span th:if="" th:text=""></span></p>
                        </div>
                    </div>
                    <div class="pull-right">
                        <div class="btn-group">
                            <div th:classappend="" class="previous">
                                <a class="btn btn-default" th:href=""><span class="glyphicon glyphicon-chevron-left"></span></a>
                            </div>
                        </div>
                        <div class="btn-group">
                            <div th:classappend="" class="next">
                                <a class="btn btn-default" th:href=""><span class="glyphicon glyphicon-chevron-right"></span></a>
                            </div>
                        </div>
                        <div class="btn-group">
                            <a th:href="" title="編集" type="button" class="btn btn-info" th:text="">編集</a>
                            <!--
                                                                <a class="btn btn-info dropdown-toggle" data-toggle="dropdown"><span class="caret"></span><span class="sr-only">Toggle Dropdown</span></a>
                                                                <ul class="dropdown-menu pull-right" role="menu">
                                                                    <li class="disabled"><a href="#">記事をコピー</a></li>
                                                                    <li><a href="">英語版</a></li>
                                                                    <li><a href="">ドイツ語版</a></li>
                                                                    <li><a href="">フランス語版</a></li>
                                                                </ul>
                            -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="wr-page-content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-9 wr-describe-main">
                    <div class="alert alert-success" th:if="">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <span th:text="">Page saved.</span>
                    </div>
                    <div th:if="">
                        <img th:src="" class="wr-post-cover" />
                    </div>
                    <div class="wr-post-body clearfix">
                        <p th:utext=""></p>
                    </div>
                    <hr/>
                    <span class="glyphicon glyphicon-user"></span> Created By <a th:href="" th:text="">Author</a>
                </div>
                <div class="col-sm-3 wr-tool-panel">
                    <dl>
                        <dt th:text="">Parent Page</dt>
                        <dd>
                            <ul class="list-unstyled">
                                <li th:if="" th:text="">Category Name</li>
                            </ul>
                        </dd>
                    </dl>
                    <dl>
                        <dt th:text="">Categories</dt>
                        <dd>
                            <ul class="list-unstyled list-inline">
                                <li th:each=""><a th:href="" th:text="">Category Name</a></li>
                            </ul>
                        </dd>
                    </dl>
                    <dl class="wr-tags">
                        <dt th:text="">Tags</dt>
                        <dd>
                            <ul class="list-unstyled list-inline list-inline-tag">
                                <li th:each=""><a th:href=""><span class="label label-default"><span class="glyphicon glyphicon-tag"></span> <span th:text=""></span></span></a></li>
                            </ul>
                        </dd>
                    </dl>
                    <dl>
                        <dt th:text="">Related Posts</dt>
                        <dd>
                            <ul class="list-unstyled">
                                <li th:each=""><a th:href=""><span class="glyphicon glyphicon-link"></span> <span th:text=""></span></a></li>
                            </ul>
                        </dd>
                    </dl>
                    <dl>
                        <dt th:text="">SEO</dt>
                        <dd>
                            <dl>
                                <dt th:text="">Title</dt>
                                <dd th:text=""></dd>
                                <dt th:text="">Description</dt>
                                <dd th:text=""></dd>
                                <dt th:text="">Keywords</dt>
                                <dd th:text=""></dd>
                            </dl>
                        </dd>
                    </dl>
                    <dl th:each="">
                        <dt th:unless="" th:text=""></dt>
                        <dd th:switch="">
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:utext=""></span>
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:text=""></span>
                            <span th:case="" th:text=""></span>
                        </dd>
                    </dl>
                    <a th:href="" data-toggle="modal" data-target="#delete-modal"><span class="glyphicon glyphicon-trash"></span> <span th:text="">ページを削除</span></a>
                    <!-- #delete-modal -->
                    <div id="delete-modal" class="modal" tabindex="-1" role="dialog" aria-hidden="true">
                        <div id="delete-dialog" class="modal-dialog">
                            <form th:action="" method="post">
                                <div th:fragment="" class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" th:text="">ページを削除</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p th:text="">本当に削除しますか？</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-default" data-dismiss="modal" th:text="">キャンセル</button>
                                        <button class="btn btn-primary" th:text="">削除</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!--/#delete-modal -->
                    <script th:inline="">
                        // <![CDATA[
                        $(function() {
                            $('#delete-modal').on('hidden.bs.modal', function() {
                                $(this).removeData('bs.modal');
                            });
                            $('img', '#wr-page-content').addClass('img-responsive');
                        });
                        // ]]>
                    </script>
                </div>
            </div>
            <div class="alert alert-warning" th:unless="">
                <strong>Not Found.</strong> May be deleted.
            </div>
        </div>
    </div>
</page-describe>